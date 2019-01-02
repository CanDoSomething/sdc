package com.zgczx.enums;

import lombok.Getter;

/**
 * @Author: Dqd
 * @Date: 2019/1/2 16:18
 * @Description:
 */
@Getter
public enum CourseEnum {

    SUB_WAIT(0,"待预约"),
    SUB_SUCCESS(1,"已被预约"),
    SUB_COURSE_INTERACT(2,"课程正在进行"),
    SUB_COURSE_FINISH(3,"课程正常结束"),
    COURSE_CANCELED(4,"课程失效"),

    ;

    private Integer code;

    private String message;

    CourseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
