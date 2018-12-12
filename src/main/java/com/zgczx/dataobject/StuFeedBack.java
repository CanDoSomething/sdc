package com.zgczx.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Auther: 陈志恒
 * @Date: 2018/12/11 21:40
 * @Description:反馈信息类
 */
@Entity
@Data
public class StuFeedBack {
    @Id
    private Integer feedbackId;
    //课程id
    private Integer  courseId;
    //学生反馈信息
    private String stuInfo;
    //学生给老师打分
    private Integer stuToScore;

}
