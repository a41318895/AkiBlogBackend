package com.akichou.runner;

import com.akichou.domain.entity.Article;
import com.akichou.repository.ArticleRepository;
import com.akichou.util.RedisCache;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.akichou.constant.SystemConstants.ARTICLE_VIEW_COUNT_REDIS_KEY_NAME;

/**
 * Redis PreHandle Runner
 *
 * @author Aki Chou
 * @date 2024/06/23 Sun.
 */
@Component
@RequiredArgsConstructor
public class RedisPreHandleRunner implements CommandLineRunner {

    private final ArticleRepository articleRepository ;
    private final RedisCache redisCache ;

    @Override
    public void run(String... args) {

        List<Article> articles = articleRepository.findAll() ;

        Map<String, Integer> articleIdAndViewCountMap = articles.stream()
                .collect(Collectors.toMap(
                        article -> article.getId().toString(),
                        article -> article.getViewCount().intValue()));

        redisCache.setCacheMap(ARTICLE_VIEW_COUNT_REDIS_KEY_NAME, articleIdAndViewCountMap);
    }
}
