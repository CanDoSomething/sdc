package com.zgczx.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Dqd on 2018/12/12.
 */
@Data
@Entity
public class StuPrepareSub {
    @Id
    private String stuPrepareSubId;
    //课程id
    private Integer courserId;
    //学生学籍号
    private String stuCode;
    //学生信用
    private Integer creditScore;
}
