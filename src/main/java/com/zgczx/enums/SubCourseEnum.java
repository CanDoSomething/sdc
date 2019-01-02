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
    SUB_CANDIDATE_SUCCESS(1,"学生预约成功"),
    SUB_CANDIDATE_FAILD(2,"学生预约失败"),
    STU_CANCEL_SUB(3,"学生取消预约"),
    TEA_CANCEL_SUB(4,"教师取消预约"),
    ;

    private Integer code;

    private String message;

    SubCourseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
