package com.akichou.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User Info Update Request
 *
 * <p>
 * This class represents the request payload for updating user information. It includes the
 * necessary fields required for modifying user details such as nickname, avatar, sex, and email.
 * All fields are mandatory and must be provided to successfully update the user's information.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/20 Sat.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "UserInfoRequest", description = "Update user info request")
public class UserInfoRequest {

    @Schema(name = "id", description = "user id")
    @NotNull(message = "ID must not be NULL !")
    private Long id;

    @Schema(name = "nickName", description = "user nick name")
    @NotBlank(message = "NickName should not be BLANK !")
    private String nickName ;

    @Schema(name = "avatar", description = "user avatar url")
    @NotBlank(message = "Avatar must not be BLANK !")
    private String avatar;

    @Schema(name = "sex", description = "user sex")
    @NotBlank(message = "Sex must not be BLANK !")
    private String sex;

    @Schema(name = "email", description = "user email")
    @NotBlank(message = "Email should not be BLANK !")
    private String email ;
}
