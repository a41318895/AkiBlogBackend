package com.akichou.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Admin User Info VO
 *
 * <p>
 * Represents the response data containing user information, permissions, and roles.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/06/24 Mon.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "AdminUserInfoVo", description = "login user detail info VO")
public class AdminUserInfoVo {

    @Schema(name = "permissions", description = "permissions login user has")
    private List<String> permissions ;

    @Schema(name = "roles", description = "roles login user has")
    private List<String> roles ;

    @Schema(name = "userInfo", description = "login user info")
    private UserInfoVo userInfo;
}
