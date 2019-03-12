package com.zgczx.repository;

import com.zgczx.dataobject.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author  Jason
 */
public interface ArticleRepository extends JpaRepository<Article,Integer> {

    /**
     * 根据标签返回文章列表
     * @param label1 文章标签
     * @param pageable 分页
     * @return  对应标签的文章
     */
    Page<Article> findByArticleLabel1(String label1, Pageable pageable);


}
