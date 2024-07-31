package com.akichou.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the information of a user VO
 *
 * <p>
 * This class provides a view object for displaying user details. It includes essential information
 * about the user, such as their username, status, contact information, and more. It is used when
 * retrieving and presenting user data.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/20 Sat.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "UserGetInfoVo", description = "Target user info VO")
public class UserGetInfoVo {

    @Schema(name = "id", description = "user id")
    private Long id;

    @Schema(name = "userName", description = "user user name")
    private String userName;

    @Schema(name = "nickName", description = "user nick name")
    private String nickName ;

    @Schema(name = "status", description = "user status ( 0 -> usable, 1 -> forbidden )")
    private String status ;

    @Schema(name = "phoneNumber", description = "user phone number")
    private String phoneNumber ;

    @Schema(name = "email", description = "user email")
    private String email ;

    @Schema(name = "sex", description = "user sex")
    private String sex;
}
