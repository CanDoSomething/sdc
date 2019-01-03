package com.zgczx.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author jason
 */
@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class FeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer feedbackId;
    /**
     * 预约课程表的id
     */
    private Integer subId;
    /**
     * 学生评分
     */
    private Integer stuScore;
    /**
     * 学生反馈内容
     */
    private String stuFeedback;
    /**
     * 教师评分
     */
    private Integer teaScore;
    /**
     * 教师反馈内容
     */
    private String teaFeedback;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}
