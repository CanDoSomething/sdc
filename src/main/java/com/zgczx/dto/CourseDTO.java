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

    /**
     * 课程编号
     */
    private Integer courseId;
    /**
     * 教师工号
     */
    private String teaCode;
    /**
     * 课程名称
     */
    private String courseName;
    /**
     * 课程日期
     */
    private Date courseDate;
    /**
     * 课程开始时间
     */
    private Date courseStartTime;
    /**
     * 课程结束时间
     */
    private Date courseEndTime;
    /**
     * 课程当前状态（0表示待预约，1表示已被预约，2表示为正在进行，3表示课程正常结束，4表示课程失效）
     */
    private Integer courseStatus;
    /**
     * 相关介绍（老师取消课程原因）
     */
    private String courseCause;
    /**
     * 上课地点
     */
    private String courseLocation;
    /**
     * 上课方式(0表示线上，1表示线下)
     */
    private Integer courseInteractive;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     *老师对象
     */
    private TeaBase teaBase;
    /**
     *反馈信息
     */
    private FeedBack feedBack;

}
