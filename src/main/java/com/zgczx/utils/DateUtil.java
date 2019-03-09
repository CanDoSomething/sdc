package com.zgczx.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public static String getNowTime(){
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = dateFormat.format(calendar.getTime());
        return nowTime;
    }
}
