package com.hualala.mapper;

import com.hualala.bean.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <br>
 * <b>功能：</b>DepartmentMapper<br>
 * <b>@author：</b>YuanChong<br>
 * <b>日期：</b>Sat Jun 01 10:34:27 CST 2019<br>
 */
@Mapper
public interface DepartmentMapper {

    /**
    * <br>
    * <b>功能：</b>新增<br>
    * <b>@param ：</b>Department<br>
    * <b> @return ：</b>Sat Jun 01 10:34:27 CST 2019<br>
    */
   int insert(Department department);

    /**
    * <br>
    * <b>功能：</b>编辑<br>
    * <b>@param ：</b>Department<br>
    * <b> @return ：</b>Sat Jun 01 10:34:27 CST 2019<br>
    */
   int update(Department department);

    /**
    * <br>
    * <b>功能：</b>删除<br>
    * <b>@param ：</b>id<br>
    * <b> @return ：</b>Sat Jun 01 10:34:27 CST 2019<br>
    */
   int delete(@Param("departmentID") Long departmentID);

    /**
    * <br>
    * <b>功能：</b>查询<br>
    * <b>@param ：</b>Department<br>
    * <b> @return ：</b>Sat Jun 01 10:34:27 CST 2019<br>
    */
    List<Department> query(Department department);

}
