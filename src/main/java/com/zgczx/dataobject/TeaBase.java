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
public class TeaBase {

    //教师工号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String teaCode;
    //教师姓名
    private String teaName;
    //教师科目
    private String teaSubject;
    //教师绑定微信号
    private String teaOpenid;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

}
