package com.zgczx.service.impl;

import com.zgczx.dataobject.Article;
import com.zgczx.dataobject.ArticleScore;
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

import static com.zgczx.utils.DateUtil.getNowTime;

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
        log.info("getArticleList--->"+"openid:"+openid+"  "+"pageSize:"+pageSize+"  "+"time:"+getNowTime());

        return articleList.getContent();
    }

    @Override
    public Article getArticleContent(Integer artId,String openid) {

        Article article = articleRepository.findOne(artId);

        if(article == null){
            String info = "根据文章编号找不到对应文章";
            log.info(info);
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,info);
        }
        log.info("getArticleContent--->"+"openid:"+openid+"  "+"artId:"+artId+"  "+"time:"+getNowTime());

        return article;
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

        ArticleScore savedArticleScore = articleScoreRepository.save(articleScore);
        if(savedArticleScore  == null){
            throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,"新评分没有保存成功，openid="+"openid"+
                    "artId="+artId+"score="+score);
        }

        log.info("putScore--->"+"openid:"+openid+"  "+"artId:"+artId+"  "+"score:"+score+"  "+"time:"+getNowTime());

        return savedArticleScore;

    }
}
