package com.akichou.repository;

import com.akichou.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * User Repository
 *
 * @author Aki Chou
 * @date 2024/06/20 Thu.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);

    List<User> findByIdIn(List<Long> ids);

    boolean existsByUserName(String userName);

    @Query("SELECT u FROM User u " +
           "WHERE (:userName IS NULL OR u.userName LIKE CONCAT('%', :userName, '%')) " +
           "AND (:phoneNumber IS NULL OR u.phoneNumber = :phoneNumber) " +
           "AND (:status IS NULL OR u.status = :status) " +
           "AND u.delFlag = 0")
    Page<User> findAllByUserNameAndPhoneAndStatusExisting(@Param("userName") String userName,
                                                          @Param("phoneNumber") String phoneNumber,
                                                          @Param("status") String status,
                                                          Pageable pageable);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    List<User> findByStatusAndDelFlag(String status, Integer delFlag);
}