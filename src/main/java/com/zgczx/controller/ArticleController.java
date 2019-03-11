package com.zgczx.controller;

import com.zgczx.VO.ResultVO;
import com.zgczx.dataobject.Article;
import com.zgczx.service.ArticleService;
import com.zgczx.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Jason
 * @date 2019/2/22 20:39
 */
@RequestMapping("/article")
@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("getArticleList")
    public ResultVO getArticleList(@RequestParam(value = "openid",defaultValue = "null")String openid,
                                   @RequestParam(value = "page")Integer page,
                                   @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){

        List<Article> articleList = articleService.getArticleList(openid,page,pageSize);

        return ResultVOUtil.success(articleList);
    }

    @PostMapping("getArticleContent")
    public ResultVO getArticleContent(@RequestParam(value = "artId") Integer artId,
                                      @RequestParam(value = "openid",defaultValue = "null") String openid){

        Article article = articleService.getArticleContent(artId,openid);

        return ResultVOUtil.success(article);
    }

    @GetMapping("getArticleScore")
    public ResultVO getArticleScore(@RequestParam(value = "openid") String openid,
                                    @RequestParam(value = "artId") Integer artId){

        return ResultVOUtil.success(articleService.getArticleScore(openid, artId));
    }

    @GetMapping("putScore")
    public ResultVO putScore(@RequestParam(value = "openid") String openid,
                             @RequestParam(value = "artId") Integer artId,
                             @RequestParam(value = "score") Integer score){

        return ResultVOUtil.success(articleService.putScore(openid, artId,score));
    }
}
