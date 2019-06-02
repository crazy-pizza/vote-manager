package com.hualala.controller;

import com.hualala.bean.User;
import com.hualala.bean.VoteDetail;
import com.hualala.bean.VoteResult;
import com.hualala.common.ResultCode;
import com.hualala.common.ResultUtils;
import com.hualala.component.UserResolver;
import com.hualala.exception.BusinessException;
import com.hualala.service.VoteResultService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/voteResult")
public class VoteResultController {
	
	@Autowired
	private VoteResultService voteResultService;

	/**
	 * 参与投票
	 * @param voteResult
	 * @param user
	 * @return
	 */
	@RequestMapping("/addVoteResult")
	public Object addVoteResult(@RequestBody VoteResult voteResult, @UserResolver User user) {
		if(user.getRoleType() == 4) {
			throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(),"管理员不能参与投票");
		}
		if(voteResult.getVoteID() == null || voteResult.getVoteID() == 0L) {
			throw new BusinessException(ResultCode.PARAMS_LOST.getCode(),"投票ID必选");
		}
		if(voteResult.getResultType() == 1 && StringUtils.isEmpty(voteResult.getVoteDetailIDs())) {
			throw new BusinessException(ResultCode.PARAMS_LOST.getCode(),"投票选项必选");
		}
		voteResultService.add(voteResult,user);
		return ResultUtils.success();
	}

	/**
	 * 投票结果报表
	 * @param voteResult
	 * @param user
	 * @return
	 */
	@RequestMapping("/voteResultReport")
	public Object voteResultReport(@RequestBody VoteResult voteResult, @UserResolver User user) throws CloneNotSupportedException {
		if(voteResult.getVoteID() == null || voteResult.getVoteID() == 0L) {
			throw new BusinessException(ResultCode.PARAMS_LOST.getCode(),"投票ID必选");
		}
		if(voteResult.getVoteType() == null || voteResult.getVoteType() == 0L) {
			throw new BusinessException(ResultCode.PARAMS_LOST.getCode(),"投票类型必传");
		}
		List<VoteDetail> list = voteResultService.voteResultReport(voteResult);
		return ResultUtils.success(list);
	}

	/**
	 * 查询未参与投票的用户
	 * @param voteResult
	 * @param user
	 * @return
	 */
	@RequestMapping("/queryUnVoteUser")
	public Object queryUnVoteUser(@RequestBody VoteResult voteResult, @UserResolver User user) {
		if(voteResult.getVoteID() == null || voteResult.getVoteID() == 0L) {
			throw new BusinessException(ResultCode.PARAMS_LOST.getCode(),"投票ID必选");
		}
		List<User> list = voteResultService.queryUnVoteUser(voteResult);
		return ResultUtils.success(list);
	}



}
