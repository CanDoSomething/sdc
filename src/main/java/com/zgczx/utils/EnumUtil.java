package com.zgczx.utils;

import com.zgczx.enums.ArticleLabelEnum;

/**
 * @author Jason
 * @date 2019/3/12 14:49
 */
public class EnumUtil {
    public static <T extends ArticleLabelEnum> T getByCode(Integer code, Class<T> enumClass) {
        //通过反射取出Enum所有常量的属性值
        for (T each: enumClass.getEnumConstants()) {
            //利用code进行循环比较，获取对应的枚举
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}

