package com.akichou.controller;

import com.akichou.customEnum.AppHttpCodeEnum;
import com.akichou.domain.ResponseResult;
import com.akichou.domain.dto.UserLoginRequest;
import com.akichou.domain.vo.BlogUserLoginVo;
import com.akichou.exception.SystemException;
import com.akichou.service.BlogLoginService;
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

/**
 * Blog Front-Stage Login Controller
 *
 * <p>
 * This controller manages front-stage operations related to user login and logout.
 * It handles basic functionalities such as user login and logout operations.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/24 Wed.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Login Controller API", description = "Front-Stage Controller about handling login")
public class BlogLoginController {

    private final BlogLoginService blogLoginService;

    @Operation(
            summary = "User Login",
            description = "Allows a user to log in with their credentials",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Login request containing username and password",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UserLoginRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully logged in",
                            content = @Content(
                                    schema = @Schema(implementation = BlogUserLoginVo.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid login request"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseResult.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "604",
                            description = "UserName can't be blank !",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
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
    @PostMapping(value = "/login")
    public ResponseResult<BlogUserLoginVo> login(@Valid @RequestBody UserLoginRequest userLoginRequest){

        if(!StringUtils.hasText(userLoginRequest.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME) ;
        }

        log.info("Login attempt with username: {}", userLoginRequest.getUserName());

        ResponseResult<BlogUserLoginVo> loginResult = blogLoginService.login(userLoginRequest);

        if (loginResult.isSuccess()) {
            log.info("Login successful for username: {}", userLoginRequest.getUserName());
        } else {
            log.warn("Login failed for username: {} with error code: {}, message: {}",
                    userLoginRequest.getUserName(), loginResult.getCode(), loginResult.getMsg());
        }

        return loginResult;
    }

    @Operation(
            summary = "User Logout",
            description = "Allows a user to log out",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully logged out",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseResult.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseResult.class)
                            )
                    )
            }
    )
    @PostMapping(value = "/logout")
    public ResponseResult<Object> logout(){

        log.info("Logout attempt");

        ResponseResult<Object> logoutResult = blogLoginService.logout() ;

        if (logoutResult.isSuccess()) {
            log.info("Logout successful");
        } else {
            log.warn("Logout failed with error code: {}, message: {}",
                    logoutResult.getCode(), logoutResult.getMsg());
        }

        return logoutResult;
    }

}