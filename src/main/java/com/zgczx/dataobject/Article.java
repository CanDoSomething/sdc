package com.zgczx.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Jason
 * @date 2019/2/22 20:44
 */
@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer artId;

    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章日期
     */
    private String articleDate;
    /**
     * 文章来源
     */
    private String articleSource;
    /**
     * 文章链接
     */
    private String articleUrl;

    private String para1;
    private String para2;
    private String para3;
    private String para4;
    private String para5;
    private String para6;
    private String para7;
    private String para8;
    private String para9;
    private String para10;
    private String para11;
    private String para12;
    private String para13;
    private String para14;
    private String para15;
    private String para16;
    private String para17;
    private String para18;
    private String para19;
    private String para20;


}
