package com.zgczx.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zgczx.dataobject.Article;
import com.zgczx.dataobject.ArticleScore;
import com.zgczx.dto.ArticleContentDTO;
import com.zgczx.enums.ResultEnum;
import com.zgczx.exception.SdcException;
import com.zgczx.repository.ArticleRepository;
import com.zgczx.repository.ArticleScoreRepository;
import com.zgczx.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.zgczx.utils.ArticleUtil.getArticlePara;

/**
 * @author Jason
 * @date 2019/2/22 20:41
 */
@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {


    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleScoreRepository articleScoreRepository;

    @Autowired
    private ArticleService articleService;

    @Override
    public List<Article> getArticleList(String openid, Integer page, Integer pageSize) {
        // openid 暂时没用到
        Pageable pageable = new PageRequest(page, pageSize);
        Page<Article> articleList =  articleRepository.findAll(pageable);

        if (articleList.getContent().isEmpty()){
            String info = "没有该页的文章";
            log.error(info);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
        }
        return articleList.getContent();
    }

    @Override
    public ArticleContentDTO getArticleContent(Integer artId) {

        Article article = articleRepository.findOne(artId);

        if(article == null){
            String info = "根据文章编号找不到对应文章";
            log.info(info);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,info);
        }

        ArticleContentDTO articleContentDTO = new ArticleContentDTO();
        articleContentDTO.setTitle(article.getTitle());
        articleContentDTO.setArticleDate(article.getArticleDate());
        articleContentDTO.setArticleSource(article.getArticleSource());
        articleContentDTO.setArticleUrl(article.getArticleUrl());
        List<JSONObject> para = getArticlePara(article);
        articleContentDTO.setArticleContent(para);

        return articleContentDTO;
    }

    @Override
    public ArticleScore getArticleScore(String openid, Integer artId) {
        return articleScoreRepository.findByOpenidAndArtId(openid,artId);
    }

    @Override
    public ArticleScore putScore(String openid, Integer artId, Integer score) {

        if(null != articleService.getArticleScore(openid,artId)){
            String info = "【文章评分】 该用户已经对该文章提交过分数";
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,info);
        }

        ArticleScore articleScore = new ArticleScore();
        articleScore.setOpenid(openid);
        articleScore.setArtId(artId);
        articleScore.setScore(score);

        return articleScoreRepository.save(articleScore);

    }
}
