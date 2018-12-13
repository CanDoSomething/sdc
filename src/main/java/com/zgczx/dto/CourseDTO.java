package com.zgczx.dto;

import com.zgczx.dataobject.StuFeedBack;
import com.zgczx.dataobject.TeaBase;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Auther: 陈志恒
 * @Date: 2018/12/10 23:42
 * @Description:课程信息
 */
@Data
@ToString
public class CourseDTO {
    private Integer courserId;
    //课程名称
    private String courseName;
    //课程日期
    private Date course_date;
    //课程开始时间
    private Date courseStartTime;
    //课程结束时间
    private Date courseEndTime;
    //课程当前状态
    private Integer courseStatus;
    //学生工号
    private String studentCode;
    //取消原因
    private String cause;
    //老师对象
    private TeaBase teaBase;
    //反馈信息
    private StuFeedBack stuFeedBack;
}
