package com.akichou.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Blog User Login View Object (VO)
 *
 * <p>
 * Represents the user information and login token returned after a successful login.
 * This VO is used to transfer user data and authentication token to the client.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/20 Sat.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "BlogUserLoginVo", description = "User Info after login VO")
public class BlogUserLoginVo {

    @Schema(name = "token", description = "user login token")
    private String token;

    @Schema(name = "userInfo", description = "user detail info")
    private UserInfoVo userInfo;
}