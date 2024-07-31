package com.akichou.repository;

import com.akichou.domain.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Comment Repository
 *
 * @author Aki Chou
 * @date 2024/06/21 Fri.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByTypeAndArticleIdAndRootId(
            String type,
            Long articleId,
            Long rootId,
            Pageable pageable
    );

    List<Comment> findByRootId(Long id);

    Page<Comment> findByTypeAndRootId(String type, Long id, Pageable pageable);
}

