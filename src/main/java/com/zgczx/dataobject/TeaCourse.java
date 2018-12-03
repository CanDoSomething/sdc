package com.zgczx.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private String courseName;
    //课程日期
    private Date course_date;
    //课程开始时间
    private Date courseStartTime;
    //课程结束时间
    private Date courseEndTime;
    //课程当前状态
    private Integer courseStatus;
    //创建时间
    private String createTime;
    //更新时间
    private String updateTime;


}
