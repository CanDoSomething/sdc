package com.zgczx.enums;

import lombok.Getter;

/**
 * @author jason
 */
@Getter
public enum UserEnum {

    /**
     * 数据库执行错误，请检查数据库操作
     */
    DB_ERROR(600,"数据库执行错误，请检查数据库操作"),

    /**
     * 该stuOpenid已经被注册
     */
    stuOpenid_not_registered(601,"该stuOpenid已经被注册"),

    /**
     * 该teaOpenid已经被注册
     */
    teaOpenid_not_registered(602,"该teaOpenid已经被注册"),





    ;

    private Integer code;

    private String message;

    UserEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
