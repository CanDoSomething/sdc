package com.zgczx.repository;

import com.zgczx.dataobject.Article;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author  Jason
 */
public interface ArticleRepository extends JpaRepository<Article,Integer> {


}
