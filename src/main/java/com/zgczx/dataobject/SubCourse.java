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
public class SubCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  subId;
    //学生学籍号
    private String  stuCode;
    //课程id
    private Integer  courseId;
    //预约状态
    private Integer  subStatus;
    //创建时间
    private Date createTime;
    //更新时间
    private Date  updateTime;



}
