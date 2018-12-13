package com.zgczx.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Dqd on 2018/12/12.
 */
@Data
@Entity
public class TeaFeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer feedBackId;
    //教师编号
    private String teaCode;
    //课程id
    private Integer  courseId;
    //给学生的反馈
    private String feedToStuContent;
    //给学生打分
    private Integer Score;
}
