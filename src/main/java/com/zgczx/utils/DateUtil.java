package com.zgczx.utils;

import java.util.Date;

/**
 * @Auther: 陈志恒
 * @Date: 2018/12/11 11:03
 * @Description:时间工具类
 */
public class DateUtil {
    /*
    * 比较date1与date2，如果date1大返回true
    * */
    public static boolean compareTime(Date date1, Date date2){
        long l = date1.getTime() - date2.getTime();
        if (l>0){
            return true;
        }else {
            return false;
        }
    }
}
