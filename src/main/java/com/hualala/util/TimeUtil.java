package com.hualala.util;

import org.joda.time.DateTime;


/**
 * @author YuanChong
 * @create 2019-06-02 13:52
 * @desc
 */
public class TimeUtil {
    /**
     * 返回当前时间的时间戳,格式yyyyMMdd
     * @return
     */
    public static Long currentDT8() {
        DateTime now = DateTime.now();
        return currentDT8(now);

    }


    /**
     * 返回时间戳,格式yyyyMMdd
     * @param time joda时间
     * @return
     */
    public static Long currentDT8(DateTime time) {
        return time.getYear() * 10000L +
                time.getMonthOfYear() * 100L +
                time.getDayOfMonth() * 1L;
    }

}
