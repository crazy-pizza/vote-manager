package com.hualala.mapper;

import com.hualala.bean.VoteResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <br>
 * <b>功能：</b>VoteResultMapper<br>
 * <b>@author：</b>YuanCHong<br>
 * <b>日期：</b>Sat Jun 01 19:23:17 CST 2019<br>
 */
@Mapper
public interface VoteResultMapper {

    /**
    * <br>
    * <b>功能：</b>新增<br>
    * <b>@param ：</b>VoteResult<br>
    * <b> @return ：</b>Sat Jun 01 19:23:17 CST 2019<br>
    */
   int insert(VoteResult voteResult);

    /**
    * <br>
    * <b>功能：</b>删除<br>
    * <b>@param ：</b>id<br>
    * <b> @return ：</b>Sat Jun 01 19:23:17 CST 2019<br>
    */
   int delete(@Param("voteID") Long voteID);

    /**
    * <br>
    * <b>功能：</b>查询<br>
    * <b>@param ：</b>VoteResult<br>
    * <b> @return ：</b>Sat Jun 01 19:23:17 CST 2019<br>
    */
   List<VoteResult> query(VoteResult voteResult);

}
