package com.hualala.bean;

import lombok.Data;

/**
 * @author YuanChong
 * @create 2019-06-01 18:12
 * @desc
 */
@Data
public class VoteDetail {
    private Long voteDetailID;
    private Long voteID;
    private String content;

    private Long sumVote;
}



