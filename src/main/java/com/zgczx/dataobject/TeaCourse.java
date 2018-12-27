package com.zgczx.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class TeaCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courserId;
    //教师工号
    private String teaCode;
    //课程名称
    @NotBlank
    private String courseName;
    //课程日期
    private Date course_date;
    //课程开始时间
    //@NotNull
    private Date courseStartTime;
    //课程结束时间
    //@NotNull
    private Date courseEndTime;
    //课程当前状态
    private Integer courseStatus;
    //创建时间
    private Date createTime;

    //学生工号
    private String studentCode;
    //取消原因
    private String cause;
    //是否在线
    @NotNull
    private Integer isOnline;
    //上课地点
    private String courseLocation;
    //更新时间
    private Date updateTime;

}
