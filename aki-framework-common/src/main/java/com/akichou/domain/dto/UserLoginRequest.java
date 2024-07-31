package com.akichou.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Login User Request
 *
 * <p>
 * Represents a request object encapsulating the user's credentials for login.
 * </p>
 *
 * <p>
 * This class contains fields for the user's username and password, which are required for authentication.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/17 Wed.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "UserLoginRequest", description = "userName and password for login request")
public class UserLoginRequest {

    @Schema(name = "userName", description = "user's login userName")
    @NotBlank(message = "UserName must not be BLANK !")
    private String userName;

    @Schema(name = "password", description = "user's login password")
    @NotBlank(message = "Password must not be BLANK !")
    private String password;
}
