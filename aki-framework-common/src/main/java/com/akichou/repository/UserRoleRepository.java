package com.akichou.repository;

import com.akichou.domain.entity.User;
import com.akichou.domain.entity.UserRole;
import java.util.List ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * UserRole Repository
 *
 * @author Aki Chou
 * @date 2024/06/24 Mon.
 */
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("SELECT ur.role.id FROM UserRole ur " +
           "WHERE :userId = ur.user.id")
    List<Long> findRoleIdsByUserId(@Param("userId") Long userId);

    void deleteByUser(User savedUser);
}
