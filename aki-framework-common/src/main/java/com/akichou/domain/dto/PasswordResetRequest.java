package com.akichou.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Password Reset Request DTO
 *
 * @author Aki Chou
 * @date 2024/07/27 Sat.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "PasswordResetRequest", description = "Contains the data of user reset password")
public class PasswordResetRequest {

    @Schema(name = "userName", description = "user user name")
    @NotBlank(message = "UserName must not be BLANK !")
    private String userName ;

    @Schema(name = "newPassword", description = "user new password")
    @NotBlank(message = "NewPassword must not be BLANK !")
    private String newPassword ;
}
