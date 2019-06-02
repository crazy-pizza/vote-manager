package com.hualala.service;

import com.hualala.bean.User;
import com.hualala.bean.Vote;
import com.hualala.bean.VoteDetail;
import com.hualala.bean.VoteResult;
import com.hualala.common.ResultCode;
import com.hualala.exception.BusinessException;
import com.hualala.mapper.UserMapper;
import com.hualala.mapper.VoteMapper;
import com.hualala.mapper.VoteResultMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <br>
 * <b>功能：</b>VoteResultService<br>
 * <b>@author：</b>YuanChong<br>
 * <b>日期：</b>Sat Jun 01 19:23:17 CST 2019<br>
 */
@Service
public class VoteResultService {

    @Autowired
    private VoteResultMapper voteResultmapper;

    @Autowired
    private VoteMapper voteMapper;

    @Autowired
    private UserMapper userMapper;

    /**
    * <br>
    * <b>功能：</b>增加<br>
    * <b>@param：</b>VoteResult<br>
    * <b>@return：
    */
    @Transactional(rollbackFor = Exception.class)
    public synchronized void add(VoteResult voteResult, User user){
        Vote vote = new Vote();
        vote.setVoteID(voteResult.getVoteID());
        List<Vote> voteList = voteMapper.query(vote);
        if(voteList.size() > 0) {
            Long voteMultipleNum = voteList.get(0).getVoteMultipleNum();
            //校验最多投票的数量
            if(voteResult.getResultType() != 2) {
                int length = voteResult.getVoteDetailIDs().split(",").length;
                if(Long.valueOf(length) > voteMultipleNum) {
                    String msg = "%s最多投%s票";
                    throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(),String.format(msg,voteList.get(0).getTitle(),voteMultipleNum));
                }
            }
            //校验是否投过票  这里不需要校验是否有投票权限  因为投票主体不能修改，查询时已经对列表做了权限判断处理
            VoteResult params = new VoteResult();
            params.setUserID(DigestUtils.md5Hex(user.getUserID().toString()));
            params.setVoteID(voteResult.getVoteID());
            List<VoteResult> results = voteResultmapper.query(params);
            if(results.size() > 0) {
                throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(),"您已参与过投票，不能重复提交");
            }
            voteResult.setUserID(DigestUtils.md5Hex(user.getUserID().toString()));
            voteResultmapper.insert(voteResult);
        }
    }


    public List<User> queryUnVoteUser(VoteResult voteResult) {
        //查询所有用户 过滤admin
        User userParams = new User();
        userParams.setFilterRole(4);
        List<User> userList = userMapper.query(userParams);
        VoteResult params = new VoteResult();
        params.setVoteID(voteResult.getVoteID());
        List<VoteResult> voteList = voteResultmapper.query(params);
        if(voteList.size() > 0) {
            //拿到所有投过票的用户ID（加密过）
            List<String> userIDList = voteList.stream().map(VoteResult::getUserID).collect(Collectors.toList());
            //删除投过票的用户
            userList.removeIf(user -> userIDList.contains(DigestUtils.md5Hex(user.getUserID().toString())));
        }
        return userList;
    }


    public List<VoteDetail> voteResultReport(VoteResult voteResult) throws CloneNotSupportedException {
        VoteResult params = new VoteResult();
        params.setVoteID(voteResult.getVoteID());
        List<VoteResult> resultList = voteResultmapper.query(params);
        //每一条结果都有多个选项 首先把选项切开
        //切割后的结果 不包含弃权票
        List<VoteResult> splitResultList = new ArrayList<>();
        for(VoteResult result : resultList) {
            if(result.getResultType() == 1) {
                for(String detailID : result.getVoteDetailIDs().split(",")) {
                    VoteResult clone = result.clone();
                    clone.setVoteDetailID(Long.valueOf(detailID));
                    splitResultList.add(clone);
                }
            }
        }
        //计算每一条明细的汇总数量
        Map<Long, Long> voteDetailCount = splitResultList.stream().collect(Collectors.groupingBy(VoteResult::getVoteDetailID, Collectors.counting()));
        VoteDetail voteDetail = new VoteDetail();
        voteDetail.setVoteID(voteResult.getVoteID());
        List<VoteDetail> detailList = voteMapper.queryDetailList(voteDetail);
        Map<Long, User> userMap = null;
        if(voteResult.getVoteType() == 1) {
            userMap = userMapper.query(new User()).stream().collect(Collectors.toMap(User::getUserID, Function.identity()));
        }
        for(VoteDetail detail:detailList) {
            Long sumVote = Optional.ofNullable(voteDetailCount.get(detail.getVoteDetailID())).orElse(0L);
            detail.setSumVote(sumVote);
            //如果是候选人投票 把用户ID替换成候选人姓名
            if(voteResult.getVoteType() == 1) {
                User user = userMap.get(Long.valueOf(detail.getContent()));
                detail.setContent(user.getRealName());
            }
        }
        //弃权票
        long giveUpCount = resultList.stream().filter(result -> result.getResultType() == 2).count();
        VoteDetail giveUp = new VoteDetail();
        giveUp.setContent("弃权");
        giveUp.setSumVote(giveUpCount);
        detailList.add(giveUp);
        return detailList;
    }
}
