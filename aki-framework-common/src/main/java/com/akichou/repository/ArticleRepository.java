package com.akichou.repository;

import com.akichou.domain.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Article Repository
 *
 * @author Aki Chou
 * @date 2024/06/19 Wed.
 */
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findByStatus(String status, Pageable pageable);

    @Query("SELECT a FROM Article a " +
            "WHERE (:categoryId is null OR :categoryId <= 0 OR a.categoryId = :categoryId) " +
            "AND a.status = '0' " +
            "ORDER BY a.createTime DESC")
    Page<Article> findArticlesByCategoryIdWithStatusZero(
                                                    @Param("categoryId") Long categoryId,
                                                    Pageable pageable);

    @Query("SELECT DISTINCT a.categoryId FROM Article a " +
           "WHERE :status = a.status " +
           "AND :delFlag = a.delFlag")
    List<Long> findDistinctCategoryIdsByStatusAndDelFlag(@Param("status") String status, @Param("delFlag") Integer delFlag);


    @Query("SELECT a FROM Article a WHERE " +
            "(:title IS NULL OR a.title LIKE CONCAT('%', :title, '%')) AND " +
            "(:summary IS NULL OR a.summary LIKE CONCAT('%', :summary, '%')) AND " +
            "a.delFlag = 0")
    Page<Article> findByTitleAndSummary(@Param("title") String title,
                                        @Param("summary") String summary,
                                        Pageable pageable) ;

    List<Article> findAllByDelFlag(Integer delFlag);
}
