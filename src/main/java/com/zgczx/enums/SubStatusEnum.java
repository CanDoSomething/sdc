package com.zgczx.enums;

import lombok.Getter;

@Getter
public enum SubStatusEnum {

    SUB_WAIT(0,"待预约"),
    SUB_SUCCESS(1,"已被预约"),
    SUB_COURSE_INTERACT(2,"课程正在进行"),
    SUB_COURSE_FINISH(3,"课程正常结束"),
    TEA_CANCEL_COURSE(4,"课程失效"),
    SUB_CANDIDATE_FAILD(5,"学生预约落选"),

    PARAM_EXCEPTION(500,"参数异常"),
    INFO_NOTFOUND_EXCEPTION(501,"信息未发现异常"),
    DATEBASE_OP_EXCEPTION(502,"数据库操作异常"),
    SUB_FAIL(503,"预约冲突"),
//    TEACHERCODE_INCORRECT(6,"教师编号不正确"),
//    COURSE_STATUS_ERROR(7, "课程状态不正确"),
//    COURSE_INFO_IS_NULL(8,"课程信息为空"),
//    TEAFEEDBACK_INFO_IS_NULL(9,"教师反馈信息为空"),
//    COURSE_OR_STU_IS_NULL(10,"课程或者学生信息为空"),
//    NOTFIND_TEACOURSE(500,"课程不存在"),
//    NOTFIND_TEACHER(501,"教师没有发现"),
//    SUB_EXIST(502,"预约课程冲突异常"),
//    SUB_FAIL(503,"预约失败"),
//    SUB_UPDATE_FAIL(504,"课程更新异常"),
//    NOTFIND_SUBCOURE(506,"预约信息没有发现"),
//    SUB_ERROR_CANCEL(507,"取消预约失败"),
//    FEED_STUFAIL(508,"学生反馈异常"),
//    NOTFIND_STUDENT(509,"学生没有发现"),

    ;

    private Integer code;

    private String message;

    SubStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
