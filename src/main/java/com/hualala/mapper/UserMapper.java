package com.hualala.mapper;

import com.hualala.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <br>
 * <b>功能：</b>UserMapper<br>
 * <b>@author：</b>YuanCHong<br>
 * <b>日期：</b>Fri May 31 17:06:25 CST 2019<br>
 */
@Mapper
public interface UserMapper {

    /**
    * <br>
    * <b>功能：</b>新增<br>
    * <b>@param ：</b>User<br>
    * <b> @return ：</b>Fri May 31 17:06:25 CST 2019<br>
    */
   int insert(User user);

    /**
    * <br>
    * <b>功能：</b>编辑<br>
    * <b>@param ：</b>User<br>
    * <b> @return ：</b>Fri May 31 17:06:25 CST 2019<br>
    */
   int update(User user);

    /**
    * <br>
    * <b>功能：</b>删除<br>
    * <b>@param ：</b>id<br>
    * <b> @return ：</b>Fri May 31 17:06:25 CST 2019<br>
    */
   int delete(@Param("userID") Long userID);

    /**
    * <br>
    * <b>功能：</b>查询<br>
    * <b>@param ：</b>User<br>
    * <b> @return ：</b>Fri May 31 17:06:25 CST 2019<br>
    */
    List<User> query(User user);


    void resetLeaderRole(@Param("departmentID") Long departmentID);
}
