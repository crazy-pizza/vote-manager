package com.hualala.service;

import com.hualala.bean.User;
import com.hualala.bean.Vote;
import com.hualala.bean.VoteDetail;
import com.hualala.bean.VoteResult;
import com.hualala.common.ResultCode;
import com.hualala.exception.BusinessException;
import com.hualala.mapper.VoteMapper;
import com.hualala.mapper.VoteResultMapper;
import com.hualala.util.TimeUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <br>
 * <b>功能：</b>VoteService<br>
 * <b>@author：</b>YuanChong<br>
 * <b>日期：</b>Sat Jun 01 18:10:08 CST 2019<br>
 */
@Service
public class VoteService {

    @Autowired
    private VoteMapper votemapper;

    @Autowired
    private VoteResultMapper voteResultMapper;

    /**
     * <br>
     * <b>功能：</b>增加<br>
     * <b>@param：</b>Vote<br>
     * <b>@return：
     */
    @Transactional(rollbackFor = Exception.class)
    public void add(Vote vote) {
        votemapper.insert(vote);
        List<VoteDetail> voteDetailList = vote.getVoteDetailList();
        for (VoteDetail voteDetail : voteDetailList) {
            if(StringUtils.isEmpty(voteDetail.getContent())) {
                throw new BusinessException(ResultCode.PARAMS_LOST.getCode(),"投票明细必填");
            }
            voteDetail.setVoteID(vote.getVoteID());
            votemapper.insertVoteDetail(voteDetail);
        }
    }


    /**
     * <br>
     * <b>功能：</b>删除<br>
     * <b>@param：</b>Vote<br>
     * <b>@return：
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Vote vote, User user) {
        List<Vote> list = votemapper.query(vote);
        if(list.size() > 0) {
            Boolean havaRight = false;
            Vote v = list.get(0);
            //admin拥有全部权限
            if(user.getRoleType() == 4) {
                havaRight = true;
            }else if(user.getRoleType() == 3 || user.getRoleType() == 2) {
                //科长或临时科长只能删除本科室的科室内部投票
                if(v.getVoteType() == 3 && user.getDepartmentID().equals(v.getDepartmentID())) {
                    havaRight = true;
                }
            }
            if(havaRight) {
                votemapper.delete(vote.getVoteID());
                votemapper.deleteDetail(vote.getVoteID());
                voteResultMapper.delete(vote.getVoteID());
            }else {
                throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(),"您没有权限删除此投票");
            }
        }
    }

    /**
     * <br>
     * <b>功能：</b>查询<br>
     * <b>@param：</b>Vote<br>
     * <b>@return：
     */
    public List<Vote> query(Vote vote, User user) {
        List<Vote> voteList = votemapper.query(vote);
        if (Objects.equals("1", vote.getIsActive())) {
            String voteIDs = voteList.stream().map(Vote::getVoteID).map(String::valueOf).collect(Collectors.joining(","));
            VoteResult voteResultParam = new VoteResult();
            voteResultParam.setVoteIDs(voteIDs);
            voteResultParam.setUserID(DigestUtils.md5Hex(user.getUserID().toString()));
            //当前用户的投票结果分组
            Map<Long, List<VoteResult>> voteResultMap = voteResultMapper.query(voteResultParam).stream().collect(Collectors.groupingBy(VoteResult::getVoteID));
            Long currentTime = TimeUtil.currentDT8();
            for (Vote v : voteList) {
                //管理员不能参与投票
                if(user.getRoleType() == 4) {
                    v.setAvailable(2);
                    continue;
                }
                if(v.getBeginTime() > currentTime || v.getEndTime() < currentTime) {
                    v.setAvailable(2);
                    continue;
                }
                //拿到当前用户的投票结果
                List<VoteResult> results = voteResultMap.get(v.getVoteID());
                //判断当前用户是否已经投过票
                if (results != null && results.size() > 0) {
                    v.setAvailable(2);
                    continue;
                }
                switch (v.getVoteType()) {
                    //候选人投票
                    case 1:
                        //拿到候选人
                        List<Long> candidateList = v.getVoteDetailList().stream().map(VoteDetail::getContent).map(Long::valueOf).collect(Collectors.toList());
                        //判断当前用户是否在候选人列表内
                        if (candidateList.contains(user.getUserID())) {
                            v.setAvailable(2);
                        } else {
                            v.setAvailable(1);
                        }
                        break;
                    case 2:
                        //海选投票只需要判断是否投过票就行了
                        v.setAvailable(1);
                        break;
                    case 3:
                        //科室内部投票需要判断当前用户所属科室
                        if(user.getDepartmentID().equals(v.getDepartmentID())) {
                            v.setAvailable(1);
                        }else {
                            v.setAvailable(2);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        return voteList;
    }

}
