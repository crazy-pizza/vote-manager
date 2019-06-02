package com.hualala.service;

import com.hualala.bean.Department;
import com.hualala.bean.User;
import com.hualala.common.ResultCode;
import com.hualala.exception.BusinessException;
import com.hualala.mapper.DepartmentMapper;
import com.hualala.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <br>
 * <b>功能：</b>DepartmentService<br>
 * <b>@author：</b>YuanChong<br>
 * <b>日期：</b>Sat Jun 01 10:34:27 CST 2019<br>
 */
@Service
public class DepartmentService {

    @Autowired
    private DepartmentMapper departmentmapper;
    @Autowired
    private UserMapper userMapper;

    /**
    * <br>
    * <b>功能：</b>增加<br>
    * <b>@param：</b>Department<br>
    * <b>@return：
    */
    @Transactional(rollbackFor = Exception.class)
    public synchronized int add(Department department){

        Department params = new Department();
        params.setDepartmentName(department.getDepartmentName());
        List<Department> list = departmentmapper.query(params);
        if(list.size() > 0) {
            throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(),"科室名称不能重复");
        }
        return departmentmapper.insert(department);
    }

    /**
    * <br>
    * <b>功能：</b>更新<br>
    * <b>@param：</b>Department<br>
    * <b>@return：
        */
    @Transactional(rollbackFor = Exception.class)
    public int update(Department department){

        Department params = new Department();
        params.setDepartmentName(department.getDepartmentName());
        params.setItselft(department.getDepartmentID());
        List<Department> list = departmentmapper.query(params);
        if(list.size() > 0) {
            throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(),"科室名称不能重复");
        }
        return departmentmapper.update(department);
    }

    /**
    * <br>
    * <b>功能：</b>删除<br>
    * <b>@param：</b>Department<br>
    * <b>@return：
        */
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long id){
        User user = new User();
        user.setDepartmentID(id);
        List<User> list = userMapper.query(user);
        if(list.size() > 0) {
            throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(),"科室下存在用户,不能删除");
        }
        return departmentmapper.delete(id);
    }

    /**
    * <br>
    * <b>功能：</b>查询<br>
    * <b>@param：</b>Department<br>
    * <b>@return：
    */
    public List<Department> query(Department department){
        List<Department> list = departmentmapper.query(department);
        String leaderIDs = list.stream().map(Department::getLeaderID).map(String::valueOf).collect(Collectors.joining(","));
        if(StringUtils.isNotEmpty(leaderIDs)) {
            User user = new User();
            user.setUserIDs(leaderIDs);
            Map<Long, User> userMap = userMapper.query(user).stream().collect(Collectors.toMap(User::getUserID, Function.identity()));
            for(Department d : list) {
                User u = userMap.get(d.getLeaderID());
                if(u != null) {
                    d.setLeaderName(u.getRealName());
                }
            }
        }
        return list;
    }

}
