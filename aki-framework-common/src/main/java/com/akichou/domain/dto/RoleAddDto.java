package com.akichou.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Add Role DTO
 *
 * <p>
 * Data Transfer Object for adding a new role.
 * </p>
 *
 * <p>
 * This DTO encapsulates the attributes required for creating a new role, including
 * role name, identification key, sorting number, status, menu permissions, and additional remarks.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/18 Thu.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "RoleAddDto", description = "Attributes about adding a new role")
public class RoleAddDto {

    @Schema(name = "roleName", description = "role name")
    @NotBlank(message = "RoleName must not be BLANK !")
    private String roleName;

    @Schema(name = "roleKey", description = "role identify key")
    @NotBlank(message = "RoleKey must not be BLANK !")
    private String roleKey;

    @Schema(name = "roleSort", description = "role sort number")
    @NotNull(message = "RoleSort must not be NULL !")
    private Integer roleSort;

    @Schema(name = "status", description = "role status ( 0 -> usable, 1 -> forbidden )")
    @NotBlank(message = "Status must not be BLANK !")
    private String status;

    @Schema(name = "menuIds", description = "menu ids role has (menu permissions)")
    @NotEmpty(message = "RoleName must not be EMPTY !")
    private List<Long> menuIds ;

    @Schema(name = "remark", description = "role remarks")
    @NotBlank(message = "Remark must not be BLANK !")
    private String remark;
}
