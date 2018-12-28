package com.zgczx.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author jason
 */
@Entity
@Data
@DynamicUpdate
public class StuBase {

    /**
     * 学生学籍号
     */
    @Id
    private String stuCode;
    /**
     * 学生姓名
     */
    private String stuName;
    /**
     *  学生年级，如2002级
     */
    private String stuLevel;
    /**
     * 中学分段，如初中或高中
     */
    private String stuGrade;
    /**
     * 班级：如 1班
     */
    private String stuClass;
    /**
     * 微信号唯一Id
     */
    private String stuOpenid;
    /**
     * 信用评分
     */
    private Integer creditScore;
    /**
     * 学生昵称
     */
    private String stuNickname;
    /**
     * 教师头像地址
     */
    private String stuHeadimgurl;
    /**
     * 插入时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

}
