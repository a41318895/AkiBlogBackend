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
 * Edit User DTO
 *
 * <p>
 * Data Transfer Object for editing a user's information.
 * </p>
 *
 * <p>
 * This DTO encapsulates the fields required for updating a user's information, including
 * validation annotations to ensure the presence of necessary values.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/19 Fri.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "UserEditDto", description = "New version user info dto")
public class UserEditDto {

    @Schema(name = "id", description = "user id")
    @NotNull(message = "ID must not be NULL !")
    private Long id ;

    @Schema(name = "userName", description = "user username")
    @NotBlank(message = "UserName must not be BLANK !")
    private String userName ;

    @Schema(name = "nickName", description = "user nick name")
    @NotBlank(message = "NickName must not be BLANK !")
    private String nickName;

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
