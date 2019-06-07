package com.hualala.controller;

import com.hualala.bean.Notice;
import com.hualala.bean.User;
import com.hualala.common.ResultCode;
import com.hualala.common.ResultUtils;
import com.hualala.component.UserResolver;
import com.hualala.exception.BusinessException;
import com.hualala.service.NoticeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {
	

	@Autowired
	private NoticeService noticeService;

	@RequestMapping("/add")
	public Object add(@RequestBody Notice notice, @UserResolver User user) {
		if (user.getRoleType() != 4) {
			throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(), "非管理员用户不能操作");
		}
		if(StringUtils.isEmpty(notice.getNoticeValue())) {
			throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(), "公告内容必填");
		}
		noticeService.add(notice);
		return ResultUtils.success();
	}

	@RequestMapping("/delete")
	public Object delete(@RequestBody Notice notice, @UserResolver User user) {
		if (user.getRoleType() != 4) {
			throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(), "非管理员用户不能操作");
		}
		if(notice.getNoticeID() == null || notice.getNoticeID() == 0L) {
			throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(), "公告ID必填");
		}
		noticeService.delete(notice.getNoticeID());
		return ResultUtils.success();
	}

	@RequestMapping("/query")
	public Object query(@RequestBody Notice notice) {
		List<Notice> noticeList = noticeService.query(notice);
		//截取前xx个公告
		if(notice.getCount() != null && notice.getCount() != 0) {
			noticeList = noticeList.subList(0,notice.getCount());
		}
		return ResultUtils.success(noticeList);
	}

}
