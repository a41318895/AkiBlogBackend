package com.akichou.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Admin User Login Vo
 *
 * <p>
 * Represents the response data containing the authentication token after successful login.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/06/24 Mon.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "AdminUserLoginVo", description = "contains user token after login VO")
public class AdminUserLoginVo {

    @Schema(name = "token", description = "login user token")
    private String token;
}