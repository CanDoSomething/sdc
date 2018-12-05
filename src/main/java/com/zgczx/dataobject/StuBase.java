package com.zgczx.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class StuBase {

    @Id
    //学生学籍号
    private String stuCode;
    //学生姓名
    private String stuName;
    //学生年级，如2002级
    private String stuLevel;
    //中学分段，如初中或高中
    private String stuGrade;
    //班级：如 1班
    private String stuClass;
    //微信号唯一Id
    private String stuOpenid;
    //插入时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    


}
