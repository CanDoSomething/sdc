package com.zgczx.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@DynamicInsert
public class UserFeedBack implements Serializable {
    //系统反馈Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //用户提交反馈id
    private String userOpenid;

    //系统反馈内容
    private String content;

    //反馈时间
    private Date insertTime;

}
