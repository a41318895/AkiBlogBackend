package com.akichou.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Change Role Status DTO
 *
 * <p>
 * Data Transfer Object for updating the status of a role.
 * </p>
 *
 * <p>
 * This DTO encapsulates the role identifier and the new status to be applied,
 * ensuring that both fields are validated for non-null and non-blank values.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/18 Thu.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "RoleChangeStatusDto", description = "Role's identifier and new status Dto")
public class RoleChangeStatusDto {

    @Schema(name = "id", description = "role id")
    @NotNull(message = "ID must not be NULL !")
    private Long id ;

    @Schema(name = "status", description = "role status ( 0 -> usable, 1 -> forbidden )")
    @NotBlank(message = "Status must not be BLANK !")
    private String status;
}
