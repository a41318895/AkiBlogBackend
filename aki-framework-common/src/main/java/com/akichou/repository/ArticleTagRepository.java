package com.akichou.repository;

import com.akichou.domain.entity.Article;
import com.akichou.domain.entity.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ArticleTag Repository
 *
 * @author Aki Chou
 * @date 2024/06/19 Wed.
 */
public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {

    void deleteByArticle(Article article);
}
