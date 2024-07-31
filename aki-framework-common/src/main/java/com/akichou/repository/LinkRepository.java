package com.akichou.repository;

import com.akichou.domain.entity.Link;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Link Repository
 *
 * @author Aki Chou
 * @date 2024/06/19 Wed.
 */
public interface LinkRepository extends JpaRepository<Link, Long> {

    List<Link> findByStatus(String status);

    @Query("SELECT l FROM Link l " +
           "WHERE (:name IS NULL OR l.name LIKE CONCAT('%', :name, '%')) " +
           "AND (:status IS NULL OR l.status = :status) " +
           "AND l.delFlag = 0")
    Page<Link> findByNameAndStatusExisting(@Param("name") String name,
                                           @Param("status") String status,
                                           Pageable pageable);

    List<Link> findAllByDelFlag(Integer delFlag) ;
}
