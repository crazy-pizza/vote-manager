package com.hualala.mapper;

import com.hualala.bean.Vote;
import com.hualala.bean.VoteDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <br>
 * <b>功能：</b>VoteMapper<br>
 * <b>@author：</b>YuanCHong<br>
 * <b>日期：</b>Sat Jun 01 18:10:08 CST 2019<br>
 */
@Mapper
public interface VoteMapper {

    /**
     * <br>
     * <b>功能：</b>新增<br>
     * <b>@param ：</b>Vote<br>
     * <b> @return ：</b>Sat Jun 01 18:10:08 CST 2019<br>
     */
    int insert(Vote vote);


    int insertVoteDetail(VoteDetail voteDetail);


    /**
     * <br>
     * <b>功能：</b>删除<br>
     * <b>@param ：</b>id<br>
     * <b> @return ：</b>Sat Jun 01 18:10:08 CST 2019<br>
     */
    int delete(@Param("voteID") Long voteID);


    int deleteDetail(@Param("voteID") Long voteID);

    /**
     * <br>
     * <b>功能：</b>查询<br>
     * <b>@param ：</b>Vote<br>
     * <b> @return ：</b>Sat Jun 01 18:10:08 CST 2019<br>
     */
    List<Vote> query(Vote vote);


    /**
     * <br>
     * <b>功能：</b>查询<br>
     * <b>@param ：</b>VoteDetail<br>
     * <b> @return ：</b>Sun Jun 02 01:14:48 CST 2019<br>
     */
    List<VoteDetail> queryDetailList(VoteDetail voteDetail);
}
