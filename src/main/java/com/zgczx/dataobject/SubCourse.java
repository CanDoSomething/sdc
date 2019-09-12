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
 * 预约关系表
 *
 * @author jason
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
public class SubCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  subId;
    /**
     * 学生学籍号
     */
    private String  stuCode;
    /**
     * 课程id
     */
    private Integer  courseId;
    /**
     * 预约状态（0表示提交预约请求，1表示预约成功，2表示为预约失败）
     */
    private Integer  subStatus;
    /**
     * 相关信息（如学生取消的原因）
     */
    private String stuCause;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
