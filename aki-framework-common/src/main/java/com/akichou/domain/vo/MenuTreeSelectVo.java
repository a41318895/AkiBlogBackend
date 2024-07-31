package com.akichou.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Menu Tree Select VO
 *
 * <p>
 * View Object for representing menu tree structure.
 * </p>
 *
 * <p>
 * This VO is used to encapsulate menu data in a tree structure,
 * where each node can have child menus represented by a list of {@link MenuTreeSelectVo}.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/17 Wed.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "MenuTreeSelectVo", description = "menu tree structure VO")
public class MenuTreeSelectVo {

    @Schema(name = "id", description = "menu id")
    private Long id;

    @Schema(name = "menuName", description = "menu name")
    private String menuName;

    @Schema(name = "parentId", description = "menu parent ID")
    private Long parentId;

    @Schema(name = "children", description = "sub menu tree")
    private List<MenuTreeSelectVo> children ;
}

