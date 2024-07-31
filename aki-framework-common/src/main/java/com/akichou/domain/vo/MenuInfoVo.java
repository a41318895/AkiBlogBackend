package com.akichou.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Menu Info VO
 *
 * <p>
 * View Object for detailed menu information.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/17 Wed.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "MenuInfoVo", description = "target menu detail info VO")
public class MenuInfoVo {

    @Schema(name = "id", description = "menu id")
    private Long id;

    @Schema(name = "menuName", description = "menu name")
    private String menuName;

    @Schema(name = "parentId", description = "menu parent ID")
    private Long parentId;

    @Schema(name = "orderNum", description = "menu order number")
    private Integer orderNum;

    @Schema(name = "path", description = "menu enter path")
    private String path;

    @Schema(name = "component", description = "menu front-end page component path")
    private String component;

    @Schema(name = "menuType", description = "menu type ( M -> 目錄, C -> 菜單, F -> 按鈕 )")
    private String menuType;

    @Schema(name = "visible", description = "menu isVisible ( 0 -> display, 1 -> hide )")
    private String visible;

    @Schema(name = "status", description = "menu status ( 0 -> usable, 1 -> forbidden )")
    private String status;

    @Schema(name = "perms", description = "menu permissions")
    private String perms;

    @Schema(name = "icon", description = "menu icon")
    private String icon;
}
