package com.zgczx.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * 教师课程表单信息
 *
 * @author Jason
 * @date 2019/1/3 17:09
 */
@Data
public class TeaCourseForm {
    /**
     * 教师工号
     */
    @NotEmpty(message = "教师工号必填")
    private String teaCode;
    /**
     * 课程名称
     */
    @NotEmpty(message = "课程名称必填")
    private String courseName;

    /**
     * 课程日期
     */
    @NotEmpty(message = "课程日期必填")
    private String courseDate;

    /**
     * 课程开始时间
     */
    @NotEmpty(message = "课程开始时间必填")
    private String courseStartTime;

    /**
     * 课程结束时间
     */
    @NotEmpty(message = "课程结束时间必填")
    private String courseEndTime;

    /**
     * 课程上课方式，上课方式(0表示线上，1表示线下)
     */
    @NotNull(message = "课程上课方式 必填，上课方式(0表示线上，1表示线下)")
    private Integer courseInteractive;

    /**
     * 上课地点
     */
    private String courseLocation;

    /**
     * 课程类型
     */
    @NotEmpty(message = "课程类型必填")
    private String courseSubject;
}
