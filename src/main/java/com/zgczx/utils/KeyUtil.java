package com.zgczx.utils;

import java.util.Random;

public class KeyUtil {
    /**
     * 生成唯一主键
     * 格式：时间+随机数
     * @return
     */
    public static synchronized String getUniqueKey(){
        Random random = new Random();
        Integer number =  random.nextInt(900000)+10; //六位随机数

        return System.currentTimeMillis()+String.valueOf(number);
    }
}
