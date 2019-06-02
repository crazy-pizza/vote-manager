package com.hualala.mapper;

import com.hualala.bean.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <br>
 * <b>功能：</b>NoticeMapper<br>
 * <b>@author：</b>YuanCHong<br>
 * <b>日期：</b>Sun Jun 02 21:56:24 CST 2019<br>
 */
@Mapper
public interface NoticeMapper {

    /**
    * <br>
    * <b>功能：</b>新增<br>
    * <b>@param ：</b>Notice<br>
    * <b> @return ：</b>Sun Jun 02 21:56:24 CST 2019<br>
    */
   int insert(Notice notice);


    /**
    * <br>
    * <b>功能：</b>删除<br>
    * <b>@param ：</b>id<br>
    * <b> @return ：</b>Sun Jun 02 21:56:24 CST 2019<br>
    */
   int delete(@Param("noticeID") Long noticeID);

    /**
    * <br>
    * <b>功能：</b>查询<br>
    * <b>@param ：</b>Notice<br>
    * <b> @return ：</b>Sun Jun 02 21:56:24 CST 2019<br>
    */
   List<Notice> query(Notice notice);

}
