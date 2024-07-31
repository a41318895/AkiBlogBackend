package com.akichou.repository;

import com.akichou.domain.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Role Repository
 *
 * @author Aki Chou
 * @date 2024/06/24 Mon.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r FROM Role r WHERE r.id IN :roleIds AND r.status = :status AND r.delFlag = 0")
    List<Role> findByIdInAndStatus(@Param("roleIds") List<Long> roleIds, @Param("status") String status);

    @Query("SELECT r FROM Role r " +
           "WHERE (:roleName IS NULL OR r.roleName LIKE CONCAT('%', :roleName, '%')) " +
           "AND (:status IS NULL OR r.status = :status) " +
           "AND r.delFlag = 0" +
           "ORDER BY r.roleSort")
    Page<Role> findAllByRoleNameAndStatus(@Param("roleName") String roleName,
                                          @Param("status") String status,
                                          Pageable pageable);

    List<Role> findByStatusAndDelFlag(String status, Integer delFlag);
}
