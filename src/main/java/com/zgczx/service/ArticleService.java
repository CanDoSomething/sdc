package com.zgczx.service;

import com.zgczx.dataobject.Article;

import java.util.List;

public interface ArticleService {

    List<Article> getArticleList(String openid, Integer page, Integer pageSize);
}
