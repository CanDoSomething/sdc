package com.zgczx.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 学生基本信息表
 *
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
     * 学生密码
     */
    private String stuPasswd;
    /**
     * 信用评分
     */
    private Integer creditScore;
    /**
     * 学生昵称
     */
    private String stuNickname;
    /**
     * 学生头像地址
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

    @Override
    public String toString() {
        return "StuBase{" + "stuCode='" + stuCode + '\'' + ", stuName='" + stuName + '\'' + ", stuLevel='" + stuLevel + '\'' + ", stuGrade='" + stuGrade + '\'' + ", stuClass='" + stuClass + '\'' + ", stuOpenid='" + stuOpenid + '\'' + ", stuPasswd='" + stuPasswd + '\'' + ", creditScore=" + creditScore + ", stuNickname='" + stuNickname + '\'' + ", stuHeadimgurl='" + stuHeadimgurl + '\'' + ", createTime=" + createTime + ", updateTime=" + updateTime + '}';
    }
}
