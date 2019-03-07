package com.zgczx.repository;

import com.zgczx.dataobject.ArticleScore;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author  Jason
 */
public interface ArticleScoreRepository extends JpaRepository<ArticleScore,Integer> {

    /**
     * 根据某个用户对某文章的评分
     * @param openid 用户openid
     * @param artId 文章编号
     * @return ArticleScore
     */
    ArticleScore findByOpenidAndArtId(String openid,Integer artId);

}
