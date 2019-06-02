package com.hualala.service;

import com.hualala.bean.Notice;
import com.hualala.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <br>
 * <b>功能：</b>NoticeService<br>
 * <b>@author：</b>YuanChong<br>
 * <b>日期：</b>Sun Jun 02 21:56:24 CST 2019<br>
 */
@Service
public class NoticeService {

    @Autowired
    private NoticeMapper noticemapper;

    /**
    * <br>
    * <b>功能：</b>增加<br>
    * <b>@param：</b>Notice<br>
    * <b>@return：
    */
    @Transactional(rollbackFor = Exception.class)
    public int add(Notice notice){
        return noticemapper.insert(notice);
    }


    /**
    * <br>
    * <b>功能：</b>删除<br>
    * <b>@param：</b>Notice<br>
    * <b>@return：
        */
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long id){
        return noticemapper.delete(id);
    }

    /**
    * <br>
    * <b>功能：</b>查询<br>
    * <b>@param：</b>Notice<br>
    * <b>@return：
    */
    @Transactional(rollbackFor = Exception.class)
    public List<Notice> query(Notice notice){
        return noticemapper.query(notice);
    }

}
