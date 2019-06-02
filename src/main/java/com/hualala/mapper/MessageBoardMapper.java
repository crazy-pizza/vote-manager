package com.hualala.mapper;

import com.hualala.bean.MessageBoard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <br>
 * <b>功能：</b>MessageBoardMapper<br>
 * <b>@author：</b>YuanCHong<br>
 * <b>日期：</b>Sun Jun 02 20:48:41 CST 2019<br>
 */
@Mapper
public interface MessageBoardMapper {

    /**
    * <br>
    * <b>功能：</b>新增<br>
    * <b>@param ：</b>MessageBoard<br>
    * <b> @return ：</b>Sun Jun 02 20:48:41 CST 2019<br>
    */
   int insert(MessageBoard messageBoard);


    /**
    * <br>
    * <b>功能：</b>删除<br>
    * <b>@param ：</b>id<br>
    * <b> @return ：</b>Sun Jun 02 20:48:41 CST 2019<br>
    */
   int delete(@Param("messageBoardID") Long messageBoardID);

    /**
    * <br>
    * <b>功能：</b>查询<br>
    * <b>@param ：</b>MessageBoard<br>
    * <b> @return ：</b>Sun Jun 02 20:48:41 CST 2019<br>
    */
   List<MessageBoard> query(MessageBoard messageBoard);

}
