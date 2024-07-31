package com.akichou.repository;

import com.akichou.domain.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Tag Repository
 *
 * @author Aki Chou
 * @date 2024/06/24 Mon.
 */
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("SELECT t FROM Tag t WHERE " +
            "(:name IS NULL OR t.name LIKE CONCAT('%', :name, '%')) AND " +
            "(:remark IS NULL OR t.remark LIKE CONCAT('%', :remark, '%')) AND " +
            "t.delFlag = 0")
    Page<Tag> findByNameAndRemark(@Param("name") String name,
                                  @Param("remark") String remark,
                                  Pageable pageable);

    List<Tag> findAllByDelFlag(Integer delFlag);
}
