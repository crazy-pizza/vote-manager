package com.hualala.controller;

import com.hualala.bean.Department;
import com.hualala.bean.User;
import com.hualala.common.ResultCode;
import com.hualala.common.ResultUtils;
import com.hualala.component.UserResolver;
import com.hualala.exception.BusinessException;
import com.hualala.service.DepartmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
	

	@Autowired
	private DepartmentService departmentService;
	

	@RequestMapping("/add")
	public Object add(@RequestBody Department department,@UserResolver User loginUser) {
		if (loginUser.getRoleType() != 4) {
			throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(), "非管理员用户不能操作");
		}
		if(StringUtils.isEmpty(department.getDepartmentName())) {
			throw new BusinessException(ResultCode.PARAMS_LOST.getCode(),"科室名称不能为空");
		}
		departmentService.add(department);
		return ResultUtils.success();
	}


	@RequestMapping("/delete")
	public Object delete(@RequestBody Department department,@UserResolver User loginUser) {
		if (loginUser.getRoleType() != 4) {
			throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(), "非管理员用户不能操作");
		}
		if(department.getDepartmentID() == null || department.getDepartmentID() == 0L) {
			throw new BusinessException(ResultCode.PARAMS_LOST.getCode(),"科室ID不能为空");
		}
		departmentService.delete(department.getDepartmentID());
		return ResultUtils.success();
	}

	@RequestMapping("/update")
	public Object update(@RequestBody Department department,@UserResolver User loginUser) {
		if (loginUser.getRoleType() != 4) {
			throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(), "非管理员用户不能操作");
		}
		if (StringUtils.isEmpty(department.getDepartmentName())) {
            throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(), "科室名称不能为空");
        }
        if (department.getDepartmentID() == null || department.getDepartmentID() == 0L) {
            throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(), "部门ID必传");
        }
		departmentService.update(department);
		return ResultUtils.success();
	}

	@RequestMapping("/query")
	public Object query(@RequestBody Department department) {
		List<Department> list = departmentService.query(department);
		return ResultUtils.success(list);
	}

}
