package com.akichou.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * User Info and Role List VO
 *
 * <p>
 * View Object representing a user's detailed information along with the roles they have.
 * </p>
 *
 * <p>
 * This VO encapsulates the user's roles in both ID and detailed form, as well as the user's
 * own detailed information. It is used in contexts where a comprehensive view of the user's
 * information and roles is required.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/19 Fri.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "UserInfoAndRoleListVo", description = "user roles and user detail ingo VO")
public class UserInfoAndRoleListVo {

    @Schema(name = "roleIds", description = "role ids user has")
    private List<Long> roleIds ;

    @Schema(name = "roles", description = "roles user has")
    private List<RoleListVo> roles ;

    @Schema(name = "user", description = "user info")
    private UserGetInfoVo user ;
}
