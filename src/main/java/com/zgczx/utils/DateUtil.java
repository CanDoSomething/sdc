package com.zgczx.utils;

import java.util.Date;

/**
 * 时间工具类
 *
 * @author 陈志恒
 * @date 2018/12/11 11:03
 */
public class DateUtil {

    /**
    * 比较date1与date2，如果date1大返回true
    * */
    public static boolean compareTime(Date date1, Date date2){
        long period = date1.getTime() - date2.getTime();
        return period > 0;
    }
}
