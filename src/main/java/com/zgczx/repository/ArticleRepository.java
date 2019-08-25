package com.zgczx.repository;

import com.zgczx.dataobject.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
   /* @Query(value = "SELECT a.* FROM article a WHERE NOT a.article_date REGEXP  '[:]' AND a.article_label1 = ?1",
            nativeQuery = true,
            countQuery = "select count(a.*) from article a ")
    Page<Article> findByArticleLabel1(String label1, Pageable pageable);*/

    @Query( value = "SELECT a FROM Article a WHERE NOT (a.articleDate like CONCAT('%','年','%') ) and a.articleLabel1 = ?1 ")
    Page<Article> findLabeledArticle(String label1, Pageable pageable);

    @Query(value = "SELECT a FROM Article a  WHERE NOT (a.articleDate like CONCAT('%','年','%') )")
    Page<Article> findAllTheArticle(Pageable pageable);
}
