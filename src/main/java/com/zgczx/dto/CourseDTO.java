package com.zgczx.dto;

import com.zgczx.dataobject.FeedBack;
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
    private Integer courseId;
    //课程名称
    private String courseName;
    //课程日期
    private Date courseDate;
    //课程开始时间
    private Date courseStartTime;
    //课程结束时间
    private Date courseEndTime;
    //课程当前状态
    private Integer courseStatus;
    //取消原因
    private String cause;
    //是否在线
    private Integer courseInteractive;
    //上课地点
    private String courseLocation;
    //更新时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //老师对象
    private TeaBase teaBase;
    //反馈对象
    private FeedBack feedBack;

}
