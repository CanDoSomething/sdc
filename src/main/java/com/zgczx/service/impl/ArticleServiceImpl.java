package com.zgczx.service.impl;

import com.zgczx.dataobject.Article;
import com.zgczx.dataobject.ArticleScore;
import com.zgczx.dto.ArticleAbstractDTO;
import com.zgczx.enums.ArticleLabelEnum;
import com.zgczx.enums.ResultEnum;
import com.zgczx.exception.SdcException;
import com.zgczx.repository.ArticleRepository;
import com.zgczx.repository.ArticleScoreRepository;
import com.zgczx.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<ArticleAbstractDTO> getArticleList(String openid, String label, Integer page, Integer pageSize) {
        // openid 暂时没用到
        Sort sort = new Sort(Sort.Direction.DESC ,"articleDate");
        Pageable pageable = new PageRequest(page, pageSize,sort);


        Page<Article> articles;
        //  若没有该标签，则返回所有内容
        // TODO 推荐模块的文章，现在推荐返回的是所有文章

        if(!label.equals(ArticleLabelEnum.RECOMMEND.getMessage())){
            articles = articleRepository.findByArticleLabel1(label,pageable);
        }else {
            articles =  articleRepository.findAll(pageable);
        }

        if (articles.getContent().isEmpty()){
            String info = "没有找到相应的文章";
            log.error(info);
            throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
        }
        log.info("getArticleList--->"+"openid:"+openid+"  "+"pageSize:"+pageSize+"  "+"time:"+getNowTime());

        List<ArticleAbstractDTO> articleAbstractDTOList = new ArrayList<>();
        for(Article article:articles.getContent()){
            ArticleAbstractDTO articleAbstractDTO = new ArticleAbstractDTO();
            BeanUtils.copyProperties(article,articleAbstractDTO);
            articleAbstractDTOList.add(articleAbstractDTO);
        }

        return articleAbstractDTOList;
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
