package com.hualala.service;

import com.github.pagehelper.PageHelper;
import com.hualala.bean.MessageBoard;
import com.hualala.bean.User;
import com.hualala.bean.Vote;
import com.hualala.bean.VoteResult;
import com.hualala.common.ResultCode;
import com.hualala.exception.BusinessException;
import com.hualala.mapper.MessageBoardMapper;
import com.hualala.mapper.VoteMapper;
import com.hualala.mapper.VoteResultMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <br>
 * <b>功能：</b>MessageBoardService<br>
 * <b>@author：</b>YuanChong<br>
 * <b>日期：</b>Sun Jun 02 20:48:41 CST 2019<br>
 */
@Service
public class MessageBoardService {

    @Autowired
    private MessageBoardMapper messageBoardmapper;

    @Autowired
    private VoteResultMapper voteResultMapper;

    @Autowired
    private VoteMapper voteMapper;

    /**
    * <br>
    * <b>功能：</b>增加<br>
    * <b>@param：</b>MessageBoard<br>
    * <b>@return：
    */
    @Transactional(rollbackFor = Exception.class)
    public int add(MessageBoard messageBoard){
        VoteResult voteResult = new VoteResult();
        voteResult.setVoteID(messageBoard.getVoteID());
        voteResult.setUserID(DigestUtils.md5Hex(messageBoard.getUserID().toString()));
        List<VoteResult> resultList = voteResultMapper.query(voteResult);
        if(resultList.size() == 0) {
            throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(),"你未参与投票,不能留言");
        }
        return messageBoardmapper.insert(messageBoard);
    }


    /**
    * <br>
    * <b>功能：</b>查询<br>
    * <b>@param：</b>MessageBoard<br>
    * <b>@return：
    */
    public List<MessageBoard> query(MessageBoard messageBoard){
        Integer startIndex = 0;
        if(messageBoard.getPageNum() != null ) {
            PageHelper.startPage(messageBoard.getPageNum(),messageBoard.getPageSize());
            startIndex = (messageBoard.getPageNum() - 1) * messageBoard.getPageSize();
        }
        List<MessageBoard> list = messageBoardmapper.query(messageBoard);
        for(MessageBoard message:list) {
            message.setFloorLevel(++startIndex);
        }
        return list;
    }

    /**
     * <br>
     * <b>功能：</b>删除<br>
     * <b>@param：</b>MessageBoard<br>
     * <b>@return：
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(MessageBoard messageBoard, User user) {
        Vote vote = new Vote();
        vote.setVoteID(messageBoard.getVoteID());
        List<Vote> voteList = voteMapper.query(vote);
        if(voteList.size() > 0) {
            Long createBy = voteList.get(0).getCreateBy();
            if(user.getRoleType() == 4 || user.getUserID().equals(createBy)) {
                messageBoardmapper.delete(messageBoard.getMessageBoardID());
            }else {
                throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(),"您不是管理员或者投票的发起人,不能删除留言");
            }
        }
    }
}
