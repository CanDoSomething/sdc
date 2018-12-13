package com.zgczx.enums.tea;

import lombok.Getter;

/**
 * Created by Dqd on 2018/12/11.
 * 课程状态
 */
@Getter
public enum CourseStatus {

    COURSE_CAN_ORDER(0,"可预约状态"),
    COURSE_ORDERED(1,"已被预约状态"),
    COURSE_CANCEL(2,"取消预约"),
    COURSE_INTERACT(3,"教师与学生互动状态"),
    COURSE_FINISH(4,"正常结束")
    ;
    private Integer code;
    private String msg;

    CourseStatus(Integer code,String msg) {
        this.code = code;
        this.msg = msg;
    }

}
