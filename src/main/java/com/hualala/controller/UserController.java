package com.hualala.controller;

import com.github.pagehelper.PageInfo;
import com.hualala.bean.User;
import com.hualala.common.ResultCode;
import com.hualala.common.ResultUtils;
import com.hualala.component.UserResolver;
import com.hualala.exception.BusinessException;
import com.hualala.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    /**
     * 登陆
     *
     * @param user
     * @param request
     * @return
     */
    @RequestMapping("/out/login")
    public Object login(@RequestBody User user, HttpServletRequest request) {
        if (StringUtils.isEmpty(user.getPassword())) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "密码必填");
        }
        if (StringUtils.isEmpty(user.getUserName())) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "账号必填");
        }
        User dbUser = userService.login(user);
        request.getSession().setAttribute("accessToken", dbUser);
        return ResultUtils.success(dbUser);
    }

    /**
     * 登出
     * @param
     * @return
     */
    @RequestMapping("/logoff")
    public Object logoff(@UserResolver User user, HttpServletRequest request) {
        request.getSession().removeAttribute("accessToken");
        return ResultUtils.success();
    }

    /**
     * 管理员添加用户
     *
     * @param user
     * @param loginUser
     * @return
     */
    @RequestMapping("/addUser")
    public Object addUser(@RequestBody User user, @UserResolver User loginUser) {
        if (loginUser.getRoleType() != 4) {
            throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(), "非管理员用户不能操作");
        }
        if (user.getDepartmentID() == null || user.getDepartmentID() == 0L) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "所属科室必选");
        }
        if (user.getRoleType() == null || user.getRoleType() == 0) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "角色必选");
        }
        if (StringUtils.isEmpty(user.getUserName())) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "账号必填");
        }
        userService.addUser(user);
        return ResultUtils.success();
    }

    /**
     * 用户用户首次登陆完善信息
     *
     * @param user
     * @return
     */
    @RequestMapping("/updateUserBaseInfo")
    public Object updateUserBaseInfo(@RequestBody User user) {
        if (user.getUserID() == null || user.getUserID() == 0L) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "用户ID必传");
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "密码必填");
        }
        if (StringUtils.isEmpty(user.getRealName())) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "真实姓名必填");
        }
        if (StringUtils.isEmpty(user.getPhone())) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "电话必填");
        }
        if (user.getSex() == null || user.getSex() == 0L) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "性别必填");
        }
        userService.updateUser(user);
        return ResultUtils.success();
    }

    /**
     * 重置密码
     *
     * @param user
     * @return
     */
    @RequestMapping("/resetPassword")
    public Object resetPassword(@RequestBody User user, @UserResolver User loginUser) {
        if (loginUser.getRoleType() != 4) {
            throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(), "非管理员用户不能操作");
        }
        if (user.getUserID() == null || user.getUserID() == 0L) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "用户ID必传");
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "密码必填");
        }
        userService.updateUser(user);
        return ResultUtils.success();
    }

    /**
     * 删除用户
     *
     * @param user
     * @return
     */
    @RequestMapping("/deleteUser")
    public Object deleteUser(@RequestBody User user, @UserResolver User loginUser) {
        if (loginUser.getRoleType() != 4) {
            throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(), "非管理员用户不能操作");
        }
        if (user.getUserID() == null || user.getUserID() == 0L) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "用户ID必传");
        }
        if (user.getDepartmentID() == null || user.getDepartmentID() == 0L) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "所属科室必传");
        }
        userService.deleteUser(user);
        return ResultUtils.success();
    }

    /**
     * 修改所属科室与角色
     *
     * @param user
     * @param loginUser
     * @return
     */
    @RequestMapping("/updateRole")
    public Object updateRole(@RequestBody User user, @UserResolver User loginUser) {
        if (loginUser.getRoleType() != 4) {
            throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(), "非管理员用户不能操作");
        }
        if (user.getUserID() == null || user.getUserID() == 0L) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "用户ID必传");
        }
        if (user.getDepartmentID() == null || user.getDepartmentID() == 0L) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "所属科室必传");
        }
        if (user.getRoleType() == null || user.getRoleType() == 0) {
            throw new BusinessException(ResultCode.PARAMS_LOST.getCode(), "角色必传");
        }
        userService.updateRole(user);
        return ResultUtils.success();
    }

    @RequestMapping("/queryUserList")
    public Object queryUserList(@RequestBody User user) {
        PageInfo<User> pageInfo = userService.queryUserList(user);
        return ResultUtils.pageSuccess(pageInfo);
    }




}
