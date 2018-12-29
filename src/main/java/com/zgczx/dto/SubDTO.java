package com.zgczx.dto;

import com.zgczx.dataobject.FeedBack;
import com.zgczx.dataobject.TeaCourse;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @ClassName: Jason
 * @Author: 陈志恒
 * @Date: 2018/12/29 12:27
 * @Description:预约对象封装类
 */
@Data
@ToString
public class SubDTO {
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
    /*课程对象*/
    private TeaCourse teaCourse;
    /*反馈对象*/
    private FeedBack feedBack;
}
