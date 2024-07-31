package com.akichou.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Add Menu DTO
 *
 * <p>
 * DTO for new menu information.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/17 Wed.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "MenuAddDto", description = "new menu info Dto")
public class MenuAddDto {

    @Schema(name = "id", description = "menu id")
    @NotNull(message = "ID must not be NULL !")
    private Long id;

    @Schema(name = "menuName", description = "menu name")
    @NotBlank(message = "MenuName must not be BLANK !")
    private String menuName;

    @Schema(name = "parentId", description = "menu parent ID")
    @NotNull(message = "ParentId must not be NULL !")
    private Long parentId;

    @Schema(name = "orderNum", description = "menu order number")
    @NotNull(message = "OrderNum must not be NULL !")
    private Integer orderNum;

    @Schema(name = "path", description = "menu enter path")
    @NotBlank(message = "Path must not be BLANK !")
    private String path;

    @Schema(name = "component", description = "menu front-end page component path")
    @NotBlank(message = "Component must not be BLANK !")
    private String component;

    @Schema(name = "isFrame", description = "Is the menu linking to outside ( 0 -> yes, 1 -> no )")
    @NotNull(message = "IsFrame must not be NULL !")
    private Integer isFrame;

    @Schema(name = "menuType", description = "menu type ( M -> 目錄, C -> 菜單, F -> 按鈕 )")
    @NotBlank(message = "MenuType must not be BLANK !")
    private String menuType;

    @Schema(name = "visible", description = "menu isVisible ( 0 -> display, 1 -> hide )")
    @NotBlank(message = "Visible must not be BLANK !")
    private String visible;

    @Schema(name = "status", description = "menu status ( 0 -> usable, 1 -> forbidden )")
    @NotBlank(message = "Status must not be BLANK !")
    private String status;

    @Schema(name = "perms", description = "menu permissions")
    @NotBlank(message = "Perms must not be BLANK !")
    private String perms;

    @Schema(name = "createTime", description = "menu created time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "CreateTime must not be NULL !")
    private Date createTime;

    @Schema(name = "icon", description = "menu icon")
    @NotBlank(message = "Icon must not be BLANK !")
    private String icon;

    @Schema(name = "remark", description = "menu remarks")
    @NotBlank(message = "Remark must not be BLANK !")
    private String remark ;
}
