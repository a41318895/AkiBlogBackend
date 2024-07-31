package com.akichou.service.impl;

import com.akichou.customEnum.AppHttpCodeEnum;
import com.akichou.domain.ResponseResult;
import com.akichou.domain.dto.ArticleAddDto;
import com.akichou.domain.dto.ArticleEditDto;
import com.akichou.domain.dto.ArticleSelectConditionsDto;
import com.akichou.domain.entity.Article;
import com.akichou.domain.entity.ArticleTag;
import com.akichou.domain.entity.Category;
import com.akichou.domain.entity.Tag;
import com.akichou.domain.vo.*;
import com.akichou.exception.SystemException;
import com.akichou.repository.ArticleRepository;
import com.akichou.repository.ArticleTagRepository;
import com.akichou.repository.CategoryRepository;
import com.akichou.repository.TagRepository;
import com.akichou.service.ArticleService;
import com.akichou.service.CategoryService;
import com.akichou.util.BeanCopyUtil;
import com.akichou.util.RedisCache;
import com.akichou.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.akichou.constant.SystemConstants.*;

/**
 * Article Service Implementation
 *
 * @author Aki Chou
 * @date 2024/07/23 Tue.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    private final CategoryService categoryService ;
    private final CategoryRepository categoryRepository ;
    private final RedisCache redisCache ;
    private final ArticleTagRepository articleTagRepository ;

    // ===== Blog Front-Stage =====

    public ResponseResult<List<HotArticleVo>> getHotArticleList() {

        // Database Process
        PageRequest pageRequest = PageRequest.of(CURRENT_PAGE, ONCE_SHOW_NUMBER);
        Page<Article> page = articleRepository.findByStatus(ARTICLE_STATUS_PUBLISHED, pageRequest);

        // Get Hot Article Ids
        List<Article> hotArticles = page.getContent();
        List<Object> hotArticleIds = hotArticles.stream()
                .map(article -> (Object) article.getId().toString())
                .toList();

        // Redis Cache Process
        List<Integer> viewCounts = redisCache.getMultiCacheMapValue(ARTICLE_VIEW_COUNT_REDIS_KEY_NAME, hotArticleIds);

        // Set View Count
        for (int i = 0; i < hotArticles.size(); i++) {
            Integer viewCount = viewCounts.get(i);
            if (viewCount != null) {
                hotArticles.get(i).setViewCount(viewCount.longValue());
            } else {
                hotArticles.get(i).setViewCount(INIT_VIEW_COUNT);
            }
        }

        // Sort Hot Articles By View Count (DESC)
        hotArticles = hotArticles.stream()
                .sorted(Comparator.comparing(Article::getViewCount).reversed())
                .collect(Collectors.toList());

        // Vo Encapsulation
        List<HotArticleVo> hotArticleVos = BeanCopyUtil.copyBeanList(hotArticles, HotArticleVo.class);

        return ResponseResult.okResult(hotArticleVos);
    }

    @Override
    public ResponseResult<PageVo<ArticleVo>> getArticleList(Integer pageNum, Integer pageSize, Long categoryId) {

        // Database Process
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);
        Page<Article> page = articleRepository.findArticlesByCategoryIdWithStatusZero(categoryId, pageRequest);

        // Get all distinct Category Ids & Category Names
        Set<Long> categoryIds = page.getContent().stream().map(Article::getCategoryId).collect(Collectors.toSet());
        Map<Long, String> categoryIdAndCategoryNameMap = categoryService.getAllById(categoryIds).stream()
                .collect(Collectors.toMap(Category::getId, Category::getName));

        // Redis Cache Process
        List<Object> articleIds = page.getContent().stream()
                .map(article -> (Object) article.getId().toString())
                .collect(Collectors.toList());
        List<Integer> viewCounts = redisCache.getMultiCacheMapValue(ARTICLE_VIEW_COUNT_REDIS_KEY_NAME, articleIds);

        List<Article> articles = page.getContent();
        for (int i = 0; i < articles.size(); i++) {
            Article article = articles.get(i);

            // Set category name
            article.setCategoryName(categoryIdAndCategoryNameMap.get(article.getCategoryId()));

            // Set view count
            Integer viewCount = viewCounts.get(i);
            if (viewCount != null) {
                article.setViewCount(viewCount.longValue());
            } else {
                article.setViewCount(INIT_VIEW_COUNT);
            }
        }

        // Sort articles by isTop
        articles = articles.stream()
                .sorted(Comparator.comparing(Article::getIsTop).reversed())
                .collect(Collectors.toList());

        // Vo Encapsulation
        List<ArticleVo> articleVos = BeanCopyUtil.copyBeanList(articles, ArticleVo.class);
        PageVo<ArticleVo> pageVo = new PageVo<>(articleVos, page.getTotalElements());

        return ResponseResult.okResult(pageVo);
    }


    @Override
    public ResponseResult<ArticleDetailVo> getArticleDetail(Long articleId) {

        // Database Process
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.ARTICLE_NOT_FOUND));

        // Vo Encapsulation
        ArticleDetailVo articleDetailVo = BeanCopyUtil.copyBean(article, ArticleDetailVo.class) ;

        // Set View Count
        Integer viewCount = redisCache.getCacheMapValue(ARTICLE_VIEW_COUNT_REDIS_KEY_NAME, articleId.toString());
        articleDetailVo.setViewCount(viewCount.longValue());

        // Set category name into ArticleDetailVo
        Category category = categoryRepository.findById(articleDetailVo.getCategoryId())
                        .orElseThrow(() -> new SystemException(AppHttpCodeEnum.CATEGORY_NOT_FOUND)) ;
        articleDetailVo.setCategoryName(category.getName());

        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult<Object> updateViewCount(Long articleId) {

        // Redis Cache Process
        redisCache.incrementCacheMapValue(ARTICLE_VIEW_COUNT_REDIS_KEY_NAME, articleId.toString(), 1);

        return ResponseResult.okResult() ;
    }

    // ===== Blog Admin =====

    @Override
    public ResponseResult<Object> addArticle(ArticleAddDto articleAddDto) {

        // Database Process - Save article
        Article article = mapArticleAddDtoToArticle(articleAddDto) ;
        Article addedArticle = articleRepository.save(article);

        // New Article's Tag Ids
        List<Long> tagIds = articleAddDto.getTags();

        if (tagIds == null || tagIds.isEmpty()) {
            return ResponseResult.okResult() ;
        }

        List<Tag> tags = tagRepository.findAllById(tagIds);

        if (tags.size() != tagIds.size()) {
            throw new SystemException(AppHttpCodeEnum.SOME_TAG_NOT_FOUND);
        }

        // Erecting ArticleTag Relationship
        List<ArticleTag> articleTags = tags.stream()
                .map(tag -> {
                    ArticleTag articleTag = new ArticleTag();
                    articleTag.setArticle(addedArticle);
                    articleTag.setTag(tag);
                    return articleTag;
                })
                .collect(Collectors.toList());

        // Database Process - Save ArticleTag
        articleTagRepository.saveAll(articleTags);

        return ResponseResult.okResult() ;
    }

    private Article mapArticleAddDtoToArticle(ArticleAddDto articleAddDto) {

        return Article.builder()
                .title(articleAddDto.getTitle())
                .content(articleAddDto.getContent())
                .summary(articleAddDto.getSummary())
                .categoryId(articleAddDto.getCategoryId())
                .thumbnail(articleAddDto.getThumbnail())
                .isTop(articleAddDto.getIsTop())
                .isComment(articleAddDto.getIsComment())
                .status(articleAddDto.getStatus())
                .viewCount(INIT_VIEW_COUNT)
                .createBy(SecurityUtil.getUserId())
                .createTime(new Date())
                .updateBy(SecurityUtil.getUserId())
                .updateTime(new Date())
                .delFlag(DEL_FLAG_EXIST)
                .build();
    }

    @Override
    public ResponseResult<PageVo<ArticlePageSelectVo>> selectArticles(Integer pageNum, Integer pageSize,
                                                                      ArticleSelectConditionsDto articleSelectConditionsDto) {
        // Database Process
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);
        Page<Article> page =
                articleRepository.findByTitleAndSummary(
                        articleSelectConditionsDto.getTitle(),
                        articleSelectConditionsDto.getSummary(),
                        pageRequest);

        // Vo Encapsulation
        List<ArticlePageSelectVo> articlePageSelectVos = BeanCopyUtil.copyBeanList(page.getContent(), ArticlePageSelectVo.class);

        return ResponseResult.okResult(new PageVo<>(articlePageSelectVos, page.getTotalElements())) ;
    }

    @Override
    public ResponseResult<ArticleInfoVo> getArticle(Long articleId) {

        // Database Process
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.ARTICLE_NOT_FOUND));

        // Get Tags
        List<ArticleTag> articleTags = articleTagRepository.findAll();

        // Get Tag Ids
        List<Long> tagIds = articleTags.stream()
                .filter(articleTag -> articleTag.getArticle().getId().equals(articleId))
                .map(articleTag -> articleTag.getTag().getId())
                .toList();

        // Vo Encapsulation
        ArticleInfoVo articleInfoVo = BeanCopyUtil.copyBean(article, ArticleInfoVo.class);

        // Set tags attributes
        articleInfoVo.setTags(tagIds);

        return ResponseResult.okResult(articleInfoVo) ;
    }

    @Override
    @Transactional
    public ResponseResult<Object> editArticle(ArticleEditDto articleEditDto) {

        // Get original article
        Article article = articleRepository.findById(articleEditDto.getId())
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.ARTICLE_NOT_FOUND));

        // Save edited article
        articleRepository.save(setAttributesForArticleEditing(article, articleEditDto));

        // Get Article tag ids
        List<Long> articleDtoTagIds = articleEditDto.getTags();

        // If sent tag list is null, remove all tag bindings
        if(articleDtoTagIds == null || articleDtoTagIds.isEmpty()) {

            articleTagRepository.deleteByArticle(article);

            return ResponseResult.okResult() ;
        }

        // Remove original tag binding
        articleTagRepository.deleteByArticle(article);

        // Find new tag binding
        List<Tag> tags = tagRepository.findAllById(articleDtoTagIds);
        if (tags.size() != articleDtoTagIds.size()) {
            throw new SystemException(AppHttpCodeEnum.SOME_TAG_NOT_FOUND);
        }

        // Adding new tag binding
        List<ArticleTag> articleTags = tags.stream()
                .map(tag -> {
                    ArticleTag articleTag = new ArticleTag();
                    articleTag.setArticle(article);
                    articleTag.setTag(tag);
                    return articleTag;
                })
                .collect(Collectors.toList());

        // Saving tag binding
        articleTagRepository.saveAll(articleTags);

        return ResponseResult.okResult() ;
    }

    private Article setAttributesForArticleEditing(Article article, ArticleEditDto articleEditDto) {

        article.setTitle(articleEditDto.getTitle());
        article.setContent(articleEditDto.getContent());
        article.setSummary(articleEditDto.getSummary());
        article.setCategoryId(articleEditDto.getCategoryId());
        article.setThumbnail(articleEditDto.getThumbnail());
        article.setIsTop(articleEditDto.getIsTop());
        article.setIsComment(articleEditDto.getIsComment());
        article.setStatus(articleEditDto.getStatus());
        article.setUpdateBy(SecurityUtil.getUserId());
        article.setUpdateTime(new Date());

        return article ;
    }

    @Override
    @Transactional
    public ResponseResult<Object> deleteArticle(Long articleId) {

        // Get original article
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.ARTICLE_NOT_FOUND));

        // Database Process - Article
        article.setDelFlag(DEL_FLAG_DELETED);
        articleRepository.save(article);

        // Database Process - ArticleTag remove
        articleTagRepository.deleteByArticle(article);

        return ResponseResult.okResult();
    }

    @Override
    public List<Article> listExistArticles() {

        return articleRepository.findAllByDelFlag(DEL_FLAG_EXIST) ;
    }
}

