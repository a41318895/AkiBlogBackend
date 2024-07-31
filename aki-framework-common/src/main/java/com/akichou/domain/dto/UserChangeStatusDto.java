package com.akichou.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Change User Status DTO
 *
 * <p>
 * Data Transfer Object for changing a user's status.
 * </p>
 *
 * <p>
 * This DTO encapsulates the fields required for updating a user's status, including
 * validation annotations to ensure the presence of necessary values.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/19 Fri.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "UserChangeStatusDto", description = "User's identifier and new status Dto")
public class UserChangeStatusDto {

    @Schema(name = "id", description = "user id")
    @NotNull(message = "ID must not be NULL !")
    private Long id ;

    @Schema(name = "status", description = "user status ( 0 -> usable, 1 -> forbidden )")
    @NotBlank(message = "Status must not be BLANK !")
    private String status ;
}
