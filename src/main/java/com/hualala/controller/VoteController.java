package com.hualala.controller;

import com.hualala.bean.User;
import com.hualala.bean.Vote;
import com.hualala.common.ResultCode;
import com.hualala.common.ResultUtils;
import com.hualala.component.UserResolver;
import com.hualala.exception.BusinessException;
import com.hualala.service.VoteService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author YuanChong
 * @create 2019-06-01 18:08
 * @desc
 */
@RestController
@RequestMapping("/vote")
public class VoteController {

    @Autowired
    private VoteService voteService;

    /**
     * 发起投票
     *
     * @param vote
     * @param user
     */
    @RequestMapping("/addVote")
    public Object addVote(@RequestBody Vote vote, @UserResolver User user) {
        if (StringUtils.isEmpty(vote.getTitle())) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "标题必填");
        }
        if (StringUtils.isEmpty(vote.getDesc())) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "描述必填");
        }
        if (vote.getBeginTime() == null || vote.getBeginTime() == 0L) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "开始时间必填");
        }
        if (vote.getEndTime() == null || vote.getEndTime() == 0L) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "结束时间必填");
        }
        if (vote.getVoteType() == null || vote.getVoteType() == 0L) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "投票类型必选");
        }
        if (vote.getVoteType() == 3 && (vote.getDepartmentID() == null || vote.getDepartmentID() == 0L)) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "科室内部投票的科室必传");
        }
        if (vote.getVoteDetailList() == null || vote.getVoteDetailList().size() == 0) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "投票详情必填");
        }
        if (vote.getBeginTime() > vote.getEndTime()) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "开始时间不能大于结束时间");
        }
        if (vote.getVoteMultipleNum() == null || vote.getVoteMultipleNum() < 1) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "请选择投票多选数量");
        }
        boolean adminVote = vote.getVoteType() == 1 || vote.getVoteType() == 2;
        boolean leaderVote = vote.getVoteType() == 3;
        if (adminVote && user.getRoleType() != 4) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "非管理员不能发起候选人投票或海选投票");
        }
        if (leaderVote && user.getRoleType() == 1) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "普通用户不能发起投票");
        }
        vote.setCreateBy(user.getUserID());
        voteService.add(vote);
        return ResultUtils.success();
    }

    /**
     * 查询发起投票的列表
     * @param vote
     * @param user
     * @return
     */
    @RequestMapping("/queryVoteList")
    public Object queryVoteList(@RequestBody Vote vote, @UserResolver User user) {
        List<Vote> voteList = voteService.query(vote,user);
        return ResultUtils.success(voteList);
    }

    /**
     * 删除投票
     * @param vote
     * @param user
     * @return
     */
    @RequestMapping("/deleteVote")
    public Object deleteVote(@RequestBody Vote vote, @UserResolver User user) {
        if (vote.getVoteID() == null || vote.getVoteID() == 0L) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "投票ID必传");
        }
        voteService.delete(vote,user);
        return ResultUtils.success();
    }


}
