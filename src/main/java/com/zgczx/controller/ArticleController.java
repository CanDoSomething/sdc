package com.zgczx.controller;

import com.zgczx.VO.ResultVO;
import com.zgczx.dataobject.Article;
import com.zgczx.service.ArticleService;
import com.zgczx.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResultVO getArticleList(@RequestParam(value = "openid")String openid,
                                   @RequestParam(value = "page")Integer page,
                                   @RequestParam(value = "pageSize")Integer pageSize){

        List<Article> articleList = articleService.getArticleList(openid,page,pageSize);

        return ResultVOUtil.success(articleList);
    }
}
