package com.akichou.repository;

import com.akichou.domain.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Category Repository
 *
 * @author Aki Chou
 * @date 2024/06/19 Wed.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByIdIn(List<Long> ids);

    List<Category> findAllByDelFlag(Integer delFlag);

    @Query("SELECT c FROM Category c " +
           "WHERE (:name IS NULL OR c.name LIKE CONCAT('%', :name, '%')) " +
           "AND (:status IS NULL OR c.status = :status) " +
           "AND c.delFlag = 0")
    Page<Category> findByNameAndStatusExisting(@Param("name") String name,
                                               @Param("status") String status,
                                               PageRequest pageRequest);
}
