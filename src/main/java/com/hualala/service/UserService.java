package com.hualala.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hualala.bean.Department;
import com.hualala.bean.User;
import com.hualala.common.ResultCode;
import com.hualala.exception.BusinessException;
import com.hualala.mapper.DepartmentMapper;
import com.hualala.mapper.UserMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <br>
 * <b>功能：</b>UserService<br>
 * <b>@author：</b>YuanChong<br>
 * <b>日期：</b>Fri May 31 17:06:25 CST 2019<br>
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DepartmentMapper departmentMapper;


    public User login(User user) {
        //加密
        String encryptPassword = DigestUtils.md5Hex(user.getPassword());
        user.setPassword(encryptPassword);
        List<User> userList = userMapper.query(user);
        if(userList == null || userList.size() == 0) {
            throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(),"账号或密码错误");
        }
        return userList.get(0);
    }

    @Transactional(rollbackFor = Exception.class)
    public synchronized void addUser(User user) {
        User params = new User();
        params.setUserName(user.getUserName());
        List<User> userList = userMapper.query(params);
        if(userList.size() > 0) {
            throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(),"账号已存在");
        }
        if(user.getRoleType() == 3) {
            userMapper.resetLeaderRole(user.getDepartmentID());
        }
        String encryptPassword = DigestUtils.md5Hex("123456");
        user.setPassword(encryptPassword);
        userMapper.insert(user);
        if(user.getRoleType() == 3) {
            updateLeader(user);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateUser(User user) {
        String encryptPassword = DigestUtils.md5Hex(user.getPassword());
        user.setPassword(encryptPassword);
        userMapper.update(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateRole(User user) {
        if(user.getRoleType() == 3) {
            userMapper.resetLeaderRole(user.getDepartmentID());
        }
        userMapper.update(user);
        if(user.getRoleType() == 3) {
            updateLeader(user);
        }
    }

    public synchronized void updateLeader(User user) {

        Department department = new Department();
        department.setDepartmentID(user.getDepartmentID());
        department.setLeaderID(user.getUserID());
        departmentMapper.update(department);
    }

    public PageInfo<User> queryUserList(User user) {
        if(user.getPageSize() != null && user.getPageSize() != -1) {
            PageHelper.startPage(user.getPageNum(),user.getPageSize());
        }
        List<User> userList = userMapper.query(user);

        Map<Long, Department> departmentMap = departmentMapper.query(new Department()).stream().collect(Collectors.toMap(Department::getDepartmentID, Function.identity()));
        for(User u : userList) {
            Department department = departmentMap.get(u.getDepartmentID());
            if(department != null) {
                u.setDepartmentName(department.getDepartmentName());
            }
        }
        PageInfo<User> pageInfo = new PageInfo(userList);
        return pageInfo;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(User user) {
        userMapper.delete(user.getUserID());
        Department department = new Department();
        department.setDepartmentID(user.getDepartmentID());
        department.setUserID(user.getUserID());
        department.setLeaderID(0L);
        departmentMapper.update(department);
    }
}
