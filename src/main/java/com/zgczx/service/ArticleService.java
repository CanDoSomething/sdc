package com.zgczx.service;

import com.zgczx.dataobject.Article;
import com.zgczx.dataobject.ArticleScore;
import com.zgczx.dto.ArticleAbstractDTO;

import java.util.List;

public interface ArticleService {


    /***
     * 获取文章列表
     * @param openid 用户唯一凭证
     * @param page  页数
     * @param pageSize 页码大小
     * @return 文章列表
     */
    List<ArticleAbstractDTO> getArticleList(String openid, String label,Integer page, Integer pageSize);

    /**
     * 获取文章内容
     * @param artId 文章编号
     * @return 文章内容
     */
    Article getArticleContent(Integer artId,String openid);

    /**
     * 获取文章评分
     * @param openid 用户openid
     * @param artId 文章编号
     * @return ArticleScore
     */
    ArticleScore getArticleScore(String openid, Integer artId);

    /**
     * 提交文章评分
     * @param openid 用户openid
     * @param artId 文章id
     * @param score 评论分数
     * @return ArticleScore
     */
    ArticleScore putScore(String openid, Integer artId, Integer score);

}
