package com.akichou.service;

import com.akichou.domain.ResponseResult;
import com.akichou.domain.dto.ArticleAddDto;
import com.akichou.domain.dto.ArticleEditDto;
import com.akichou.domain.dto.ArticleSelectConditionsDto;
import com.akichou.domain.entity.Article;
import com.akichou.domain.vo.*;

import java.util.List;

/**
 * Article Service Interface
 *
 * @author Aki Chou
 * @date 2024/06/25 Tue.
 */
public interface ArticleService {

    // BLog Client Side
    ResponseResult<List<HotArticleVo>> getHotArticleList();

    ResponseResult<PageVo<ArticleVo>> getArticleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult<ArticleDetailVo> getArticleDetail(Long articleId) ;

    ResponseResult<Object> updateViewCount(Long articleId);

    // Blog Admin
    ResponseResult<Object> addArticle(ArticleAddDto articleAddDto);

    ResponseResult<PageVo<ArticlePageSelectVo>> selectArticles(Integer pageNum, Integer pageSize, ArticleSelectConditionsDto articleSelectConditionsDto);

    ResponseResult<ArticleInfoVo> getArticle(Long articleId);

    ResponseResult<Object> editArticle(ArticleEditDto articleEditDto);

    ResponseResult<Object> deleteArticle(Long articleId);

    List<Article> listExistArticles();
}
