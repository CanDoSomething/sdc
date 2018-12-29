package com.zgczx.enums;

import lombok.Getter;

/**
 * @Author: Dqd
 * @Date: 2018/12/29 13:13
 * @Description:
 */
@Getter
public enum SubCourseEnum {

    SUB_WAIT(0,"提交预约请求"),
    SUB_SUCCESS(1,"预约成功"),
    SUB_FAILURE(2,"预约失败"),
    ;

    private Integer code;

    private String message;

    SubCourseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
