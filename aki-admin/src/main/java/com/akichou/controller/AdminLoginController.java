package com.akichou.controller;

import com.akichou.customEnum.AppHttpCodeEnum;
import com.akichou.domain.ResponseResult;
import com.akichou.domain.dto.UserLoginRequest;
import com.akichou.domain.entity.User;
import com.akichou.domain.vo.*;
import com.akichou.exception.SystemException;
import com.akichou.service.BlogAdminLoginService;
import com.akichou.service.MenuService;
import com.akichou.service.RoleService;
import com.akichou.util.BeanCopyUtil;
import com.akichou.util.SecurityUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Blog Admin Login Related Handling Controller
 *
 * <p>
 * This controller manages operations related to admin login in the blog CMS(Content Management System)
 * </p>
 *
 * <p>
 * It handles functionalities such as login, logout, get user info, and get routers' information.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/24 Wed.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping
@Tag(name = "Admin Login Controller API", description = "Controller about admin login")
public class AdminLoginController {

    private final BlogAdminLoginService blogAdminLoginService ;
    private final MenuService menuService ;
    private final RoleService roleService ;

    @Operation(
            summary = "Login",
            description = "Login in admin content management system",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request Class encapsulated userName and password",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = UserLoginRequest.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Return token when login successfully",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = AdminUserLoginVo.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid login request"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "604",
                            description = "UserName can't be blank !",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "605",
                            description = "UserName or Password is wrong !",
                            content = @Content(
                                    schema = @Schema(implementation = SystemException.class)
                            )
                    )
            }
    )
    @PostMapping(value = "/user/login")
    public ResponseResult<AdminUserLoginVo> adminLogin(@Valid @RequestBody UserLoginRequest userLoginRequest){

        if(!StringUtils.hasText(userLoginRequest.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME) ;
        }

        log.info("Login attempt with username: {}", userLoginRequest.getUserName());

        ResponseResult<AdminUserLoginVo> loginResult = blogAdminLoginService.adminLogin(userLoginRequest);

        if (loginResult.isSuccess()) {
            log.info("Login successful for username: {}", userLoginRequest.getUserName());
        } else {
            log.warn("Login failed for username: {} with error code: {}, message: {}",
                    userLoginRequest.getUserName(), loginResult.getCode(), loginResult.getMsg());
        }

        return loginResult;
    }

    @Operation(
            summary = "Logout",
            description = "Logout in admin content management system",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Return the message about successful logout",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    )
            }
    )
    @PostMapping(value = "/user/logout")
    public ResponseResult<Object> logout() {

        log.info("Logout attempt");

        ResponseResult<Object> logoutResult = blogAdminLoginService.logout();

        if (logoutResult.isSuccess()) {
            log.info("Logout successful");
        } else {
            log.warn("Logout failed with error code: {}, message: {}",
                    logoutResult.getCode(), logoutResult.getMsg());
        }

        return logoutResult;
    }

    @Operation(
            summary = "Get the information of admin user",
            description = "Get the userInfo, permissions, roles information of current admin user when login",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "UserInfo, permissions, roles information",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = AdminUserInfoVo.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "613",
                            description = "User Not Found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    )
            }
    )
    @GetMapping(value = "/getInfo")
    public ResponseResult<AdminUserInfoVo> getAdminUserInfo(){

        // Get current user
        User user = SecurityUtil.getLoginUser().getUser();

        // Get user permissions and role keys
        List<String> perms = menuService.getPermsByUserId(user.getId());
        List<String> roleKeys = roleService.getRoleKeysByUserId(user.getId());

        // Vo Encapsulation
        UserInfoVo userInfoVo = BeanCopyUtil.copyBean(user, UserInfoVo.class);
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roleKeys, userInfoVo);

        log.info("Fetched info for user: {}", user.getUserName());

        return ResponseResult.okResult(adminUserInfoVo);
    }

    @Operation(
            summary = "Get all menus",
            description = "Get all menus which current admin user has",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "All menus list Vo",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = MenuRoutersVo.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "613",
                            description = "User Not Found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    )
            }
    )
    @GetMapping(value = "/getRouters")
    public ResponseResult<MenuRoutersVo> getMenuRoutersInfo() {

        log.info("Fetching menu routers");

        Long userId = SecurityUtil.getUserId() ;
        List<MenuVo> menuVos = menuService.getMenuRoutersInfo(userId) ;

        log.info("Fetched menu routers for user ID: {}", userId);

        return ResponseResult.okResult(new MenuRoutersVo(menuVos));
    }
}
