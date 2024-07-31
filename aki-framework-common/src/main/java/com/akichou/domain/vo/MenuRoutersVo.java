package com.akichou.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Menu Routers Vo
 *
 * <p>
 * Represents the response data containing menu information.
 * </p>
 *
 * <p>
 * This VO encapsulates a list of menus accessible to the current admin user.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/17 Wed.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "MenuRoutersVo", description = "login user menu routers display VO")
public class MenuRoutersVo {

    @Schema(name = "menus", description = "login user accessible menus (has the permission)")
    private List<MenuVo> menus;
}
