package com.akichou.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * Represents the information of a menu VO
 *
 * <p>
 * This class provides the details of a menu, including its properties, such as the menu name,
 * parent ID, order number, and more. It is used to display and manage menu items within the application.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/20 Sat.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Tag(name = "MenuVo", description = "Menu info VO")
public class MenuVo {

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

    @Schema(name = "isFrame", description = "Is the menu linking to outside ( 0 -> yes, 1 -> no )")
    private Integer isFrame;

    @Schema(name = "menuType", description = "menu type ( M -> 目錄, C -> 菜單, F -> 按鈕 )")
    private String menuType;

    @Schema(name = "visible", description = "menu isVisible ( 0 -> display, 1 -> hide )")
    private String visible;

    @Schema(name = "status", description = "menu status ( 0 -> usable, 1 -> forbidden )")
    private String status;

    @Schema(name = "perms", description = "menu permissions")
    private String perms;

    @Schema(name = "createTime", description = "menu created time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Schema(name = "icon", description = "menu icon")
    private String icon;

    @Schema(name = "children", description = "child menu list")
    private List<MenuVo> children ;
}
