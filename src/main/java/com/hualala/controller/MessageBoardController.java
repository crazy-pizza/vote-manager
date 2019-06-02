package com.hualala.controller;

import com.hualala.bean.MessageBoard;
import com.hualala.bean.User;
import com.hualala.common.ResultCode;
import com.hualala.common.ResultUtils;
import com.hualala.component.UserResolver;
import com.hualala.exception.BusinessException;
import com.hualala.service.MessageBoardService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messageBoard")
public class MessageBoardController {
	

	@Autowired
	private MessageBoardService messageBoardService;

	@RequestMapping("/add")
	public Object add(@RequestBody MessageBoard messageBoard,@UserResolver User user) {
		if(messageBoard.getVoteID() == null || messageBoard.getVoteID() == 0L) {
			throw new BusinessException(ResultCode.PARAMS_LOST.getCode(),"投票ID必选");
		}
		if(StringUtils.isEmpty(messageBoard.getContent())) {
			throw new BusinessException(ResultCode.PARAMS_LOST.getCode(),"请填写留言详情");
		}
		messageBoard.setUserID(user.getUserID());
		messageBoardService.add(messageBoard);
		return ResultUtils.success();
	}

	@RequestMapping("/delete")
	public Object delete(@RequestBody MessageBoard messageBoard,@UserResolver User user) {
		if(messageBoard.getMessageBoardID() == null || messageBoard.getMessageBoardID() == 0L) {
			throw new BusinessException(ResultCode.PARAMS_LOST.getCode(),"留言ID必选");
		}
		if(messageBoard.getVoteID() == null || messageBoard.getVoteID() == 0L) {
			throw new BusinessException(ResultCode.PARAMS_LOST.getCode(),"投票ID必选");
		}
		messageBoardService.delete(messageBoard,user);
		return ResultUtils.success();
	}

	@RequestMapping("/query")
	public Object query(@RequestBody MessageBoard messageBoard,@UserResolver User user) {
		if(messageBoard.getVoteID() == null || messageBoard.getVoteID() == 0L) {
			throw new BusinessException(ResultCode.PARAMS_LOST.getCode(),"投票ID必选");
		}
		List<MessageBoard> list = messageBoardService.query(messageBoard);
		return ResultUtils.success(list);
	}
}
