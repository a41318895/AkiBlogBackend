package com.akichou.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Role Menu Tree Select Vo
 *
 * <p>
 * View Object representing role menu tree structure.
 * </p>
 *
 * <p>
 * This VO encapsulates role-specific menu tree data, including the menu tree structure
 * represented by a list of {@link MenuTreeSelectVo} and the list of menu IDs that the role has access to.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/17 Wed.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "RoleMenuTreeSelectVo", description = "role menu tree VO")
public class RoleMenuTreeSelectVo {

    @Schema(name = "menus", description = "role menu tree list")
    private List<MenuTreeSelectVo> menus ;

    @Schema(name = "checkedKeys", description = "menu ids role has")
    private List<Long> checkedKeys ;
}
