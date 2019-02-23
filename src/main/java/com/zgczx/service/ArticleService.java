package com.zgczx.service;

import com.zgczx.dataobject.Article;
import com.zgczx.dto.ArticleContentDTO;

import java.util.List;

public interface ArticleService {

    /***
     * 获取文章列表
     * @param openid 用户唯一凭证
     * @param page  页数
     * @param pageSize 页码大小
     * @return 文章列表
     */
    List<Article> getArticleList(String openid, Integer page, Integer pageSize);

    /**
     * 获取文章内容
     * @param artId 文章编号
     * @return 文章内容
     */
    ArticleContentDTO getArticleContent(Integer artId);
}
