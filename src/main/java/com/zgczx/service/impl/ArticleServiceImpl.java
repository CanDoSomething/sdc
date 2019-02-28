package com.zgczx.service.impl;

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
        articleContentDTO.setArticleContent("<p>　　2月18日，教育部与辽宁省、山东省、重庆市、宁波市在京签署《推进共建" +
                "“一带一路”教育行动国际合作备忘录》，以推动相关省市积极对接“一带一路”倡议，发挥自身区位优势，着力" +
                "提高教育对外开放水平，写好教育“奋进之笔”，全面推进共建“一带一路”教育行动。教育部党组书记、部长陈" +
                "宝生出席签约仪式并讲话。教育部党组成员、副部长田学军主持签约座谈会并与辽宁省副省长李金科，山东省副省" +
                "长于杰，重庆市副市长屈谦，宁波市委副书记、市长裘东耀签署备忘录。</p>");

        //List<JSONObject> para = getArticlePara(article);
        //articleContentDTO.setArticleContent(para);

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
