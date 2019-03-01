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
    /**
     * 一级标签
     */
    private String articleLabel1;
    /**
     * 二级标签
     */
    private String articleLabel2;
    /**
     * 图片地址
     */
    private String articleImg;
    /**
     * 文章内容
     */
    private String articleContent;


}
