package com.zgczx.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author Jason
 * @date 2019/2/23 14:28
 */
@Data
@ToString
public class ArticleContentDTO {

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
     * 文章内容
     */
    private String articleContent;
}
