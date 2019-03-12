package com.zgczx.dto;

import lombok.Data;

/**
 * @author Jason
 * @date 2019/3/12 14:20
 */
@Data
public class ArticleAbstractDTO {

    /**
     * 文章id
     */
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
}
