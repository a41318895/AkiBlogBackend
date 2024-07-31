package com.akichou.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Register User Request
 *
 * <p>
 * This class represents the request payload for registering a new user.
 * It includes the necessary information required for user registration, such as username, nickname,
 * phone number, email, and password. All fields are mandatory and must be provided by the user.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/20 Sat.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "UserRegisterRequest", description = "new user register info request")
public class UserRegisterRequest {

    @Schema(name = "userName", description = "user username")
    @NotBlank(message = "UserName should not be BLANK !")
    private String userName ;

    @Schema(name = "nickName", description = "user nick name")
    @NotBlank(message = "NickName should not be BLANK !")
    private String nickName ;

    @Schema(name = "phoneNumber", description = "user phoneNumber")
    @NotBlank(message = "phoneNumber should not be BLANK !")
    private String phoneNumber ;

    @Schema(name = "email", description = "user email")
    @NotBlank(message = "Email should not be BLANK !")
    private String email ;

    @Schema(name = "password", description = "user password")
    @NotBlank(message = "Password should not be BLANK !")
    private String password ;
}
