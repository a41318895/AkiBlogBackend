package com.akichou.job;

import com.akichou.domain.entity.Article;
import com.akichou.repository.ArticleRepository;
import com.akichou.util.RedisCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.akichou.constant.SystemConstants.ARTICLE_VIEW_COUNT_REDIS_KEY_NAME;

/**
 * Update ViewCount Scheduled Job
 *
 * @author Aki Chou
 * @date 2024/06/23 Sun.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateViewCountJob {

    private final RedisCache redisCache;
    private final ArticleRepository articleRepository;

    @Scheduled(cron = "0/50 * * * * ?")
    public void updateViewCount() {
        //log.info("定時任務執行完畢 !") ;

        Map<Object, Object> cacheMap = redisCache.getCacheMap(ARTICLE_VIEW_COUNT_REDIS_KEY_NAME);

        List<Article> articles = cacheMap.entrySet().stream()
                .map(entry -> {
                    try {

                        Long articleId = toLong(entry.getKey());
                        Long viewCount = toLong(entry.getValue());
                        Optional<Article> articleOptional = articleRepository.findById(articleId);
                        if (articleOptional.isPresent()) {

                            Article article = articleOptional.get();
                            article.setViewCount(viewCount);
                            return article;
                        } else {

                            log.warn("Article with ID {} not found in database.", articleId);
                            return null;
                        }
                    } catch (NumberFormatException | ClassCastException e) {

                        log.error("Invalid data format in cache: {}", entry, e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (!articles.isEmpty()) {

            articleRepository.saveAll(articles);
        }
    }

    private Long toLong(Object value) {
        if (value instanceof String) {
            return Long.valueOf((String) value);
        } else if (value instanceof Integer) {
            return ((Integer) value).longValue();
        } else if (value instanceof Long) {
            return (Long) value;
        } else {
            throw new ClassCastException("Cannot cast value to Long: " + value);
        }
    }
}

    // *  *  * *  *  ?
    // 秒 分 時 日 月 周

    // * -> 任意
    // 1,2,3 -> 指定每個 1,2,3
    // 1-5 -> 指定1到5
    // 10/5 -> from/interval

    // ? -> 沒有確切的值 (只能用在 日 跟 周)
    // W -> 彈性調整至最接近此日的工作日 (只能用在 日)
    // L -> 最後一個日子 (只能用在 日 跟 周) ex. (日)L-2 本月倒數第二天, (日)LW 最後一個工作日
    // # -> 第幾個星期幾 (只能用在 周) ex. 6#3 第三個星期五


