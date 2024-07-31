package com.akichou.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Role List Conditions DTO
 *
 * <p>
 * Data Transfer Object for filtering role information based on certain conditions.
 * </p>
 *
 * <p>
 * This DTO encapsulates the conditions used for filtering roles, including the role name and status.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/18 Thu.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "RoleSelectConditionsDto", description = "Conditions to filter role dto")
public class RoleSelectConditionsDto {

    @Schema(name = "roleName", description = "role name")
    private String roleName ;

    @Schema(name = "status", description = "role status ( 0 -> usable, 1 -> forbidden )")
    private String status ;
}
