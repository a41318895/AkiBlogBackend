package com.akichou.repository;

import com.akichou.domain.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Menu Repository
 *
 * @author Aki Chou
 * @date 2024/06/24 Mon.
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("SELECT m.perms FROM Menu m " +
           "WHERE m.menuType IN (:menuTypes) " +
           "AND m.status = :status " +
           "AND m.delFlag = 0")
    List<String> findPermsByMenuTypes(@Param("menuTypes") List<String> menuTypes, @Param("status") String status);

    @Query("SELECT m FROM Menu m " +
            "WHERE m.menuType IN (:menuTypes) " +
            "AND m.status = :status " +
            "AND m.delFlag = 0" +
            "ORDER BY m.parentId, m.orderNum")
    List<Menu> findByMenuTypesOrdered(@Param("status") String status, @Param("menuTypes") List<String> menuTypes) ;

    @Query("SELECT m FROM Menu m WHERE m.id IN :menuIds AND m.status = :status AND m.menuType IN (:menuTypes) AND m.delFlag = 0")
    List<Menu> findByIdAndMenuTypes(@Param("menuIds") List<Long> menuIds,
                                              @Param("status") String status,
                                              @Param("menuTypes") List<String> menuTypes);


    @Query("SELECT m FROM Menu m " +
            "WHERE (:menuName IS NULL OR m.menuName LIKE CONCAT('%', :menuName, '%')) " +
            "AND (:status IS NULL OR m.status = :status) " +
            "AND m.delFlag = 0" +
            "ORDER BY m.parentId, m.orderNum")
    List<Menu> findAllByMenuNameAndStatusOrdered(@Param("menuName") String menuName,
                                                 @Param("status") Integer status);

    Menu findByIdAndStatusAndDelFlag(Long menuId, String status, Integer delFlag);

    @Query("SELECT m.parentId FROM Menu m")
    List<Long> findAllParentIds();
}