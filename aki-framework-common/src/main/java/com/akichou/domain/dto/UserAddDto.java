package com.akichou.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Add User DTO
 *
 * <p>
 * Data Transfer Object representing the attributes required for adding a new user.
 * </p>
 *
 * <p>
 * This DTO encapsulates the necessary fields for creating a new user, including their
 * username, nickname, password, status, email, phone number, sex, and the roles they have.
 * It is used in the context of user creation operations.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/19 Fri.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "UserAddDto", description = "Attributes about adding a new user")
public class UserAddDto {

    @Schema(name = "userName", description = "user username")
    @NotBlank(message = "UserName must not be BLANK !")
    private String userName ;

    @Schema(name = "nickName", description = "user nick name")
    @NotBlank(message = "NickName must not be BLANK !")
    private String nickName;

    @Schema(name = "password", description = "user password")
    @NotBlank(message = "Password must not be BLANK !")
    private String password;

    @Schema(name = "status", description = "user status ( 0 -> usable, 1 -> forbidden )")
    @NotBlank(message = "Status must not be BLANK !")
    private String status ;

    @Schema(name = "email", description = "user email")
    @NotBlank(message = "Email must not be BLANK !")
    private String email;

    @Schema(name = "phoneNumber", description = "user phone number")
    @NotBlank(message = "PhoneNumber must not be BLANK !")
    private String phoneNumber ;

    @Schema(name = "sex", description = "user sex")
    @NotBlank(message = "Sex must not be BLANK !")
    private String sex;

    @Schema(name = "roleIds", description = "role ids user has")
    @NotEmpty(message = "RoleIds must not be EMPTY !")
    private List<Long> roleIds ;
}
