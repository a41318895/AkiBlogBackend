package com.akichou.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Role List VO
 *
 * <p>
 * View Object for listing all roles.
 * </p>
 *
 * <p>
 * This VO encapsulates the details of roles including ID, name, identification key,
 * sort number, status, creation and update information, and additional remarks.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/18 Thu.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "RoleListVo", description = "list all roles VO")
public class RoleListVo {

    @Schema(name = "id", description = "role id")
    private Long id ;

    @Schema(name = "roleName", description = "role name")
    private String roleName;

    @Schema(name = "roleKey", description = "role identify key")
    private String roleKey;

    @Schema(name = "roleSort", description = "role sort number")
    private Integer roleSort;

    @Schema(name = "status", description = "role status ( 0 -> usable, 1 -> forbidden )")
    private String status;

    @Schema(name = "createBy", description = "ID role created by")
    private Long createBy ;

    @Schema(name = "createTime", description = "role created time")
    private Date createTime ;

    @Schema(name = "updateBy", description = "ID role updated by")
    private Long updateBy ;

    @Schema(name = "updateTime", description = "role updated time")
    private Date updateTime ;

    @Schema(name = "remark", description = "role remarks")
    private String remark;
}
