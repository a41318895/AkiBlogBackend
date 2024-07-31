package com.akichou.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Role Info Vo
 *
 * <p>
 * View Object representing filtered roles' information.
 * </p>
 *
 * <p>
 * This VO encapsulates the detailed information about roles, including role name, identification key,
 * sorting number, status, menu permissions, create and update time, and additional remarks.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/18 Thu.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "RoleInfoVo", description = "role detail info VO")
public class RoleInfoVo {

    @Schema(name = "id", description = "role id")
    private Long id;

    @Schema(name = "roleName", description = "role name")
    private String roleName;

    @Schema(name = "roleKey", description = "role identify key")
    private String roleKey;

    @Schema(name = "roleSort", description = "role sort number")
    private Integer roleSort;

    @Schema(name = "status", description = "role status ( 0 -> usable, 1 -> forbidden )")
    private String status;

    @Schema(name = "menuIds", description = "menu ids role has (menu permissions)")
    private List<Long> menuIds ;

    @Schema(name = "createTime", description = "role created time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime ;

    @Schema(name = "updateTime", description = "role updated time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime ;

    @Schema(name = "remark", description = "role remarks")
    private String remark;
}
