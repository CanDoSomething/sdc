package com.zgczx.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;


/**
 * @author  Jason
 */
@Entity
@Data
@DynamicUpdate
public class TeaCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;
    /**
     * 教师工号
     */
    private String teaCode;
    /**
     * 课程名称
     */
    @NotBlank
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
     * 课程当前状态（0表示待预约，1表示已被预约，2表示为失效）
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

}
