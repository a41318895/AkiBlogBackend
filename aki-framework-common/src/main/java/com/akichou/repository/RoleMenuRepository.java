package com.akichou.repository;

import com.akichou.domain.entity.Role;
import com.akichou.domain.entity.RoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * RoleMenu Repository
 *
 * @author Aki Chou
 * @date 2024/06/24 Mon.
 */
public interface RoleMenuRepository extends JpaRepository<RoleMenu, Long> {

    @Query("SELECT rm.menu.id FROM RoleMenu rm WHERE rm.role.id IN :roleIds")
    List<Long> findMenuIdsByRoleIds(@Param("roleIds") List<Long> roleIds);

    List<RoleMenu> findByRoleId(Long roleId);

    void deleteByRole(Role role);
}
