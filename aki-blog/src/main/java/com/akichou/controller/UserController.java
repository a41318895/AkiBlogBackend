package com.akichou.controller;

import com.akichou.domain.ResponseResult;
import com.akichou.domain.dto.CheckInfoDto;
import com.akichou.domain.dto.PasswordResetRequest;
import com.akichou.domain.dto.UserRegisterRequest;
import com.akichou.domain.dto.UserInfoRequest;
import com.akichou.domain.vo.UserInfoVo;
import com.akichou.exception.SystemException;
import com.akichou.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Blog Front-Stage User Controller
 *
 * <p>
 * This controller manages operations related to user management in the front stage.
 * It handles user information retrieval, user profile updates, and user registration.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/24 Wed.
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User Controller API", description = "Front-Stage Controller about handling users")
public class UserController {

    private final UserService userService ;

    @Operation(
            summary = "Get User Information",
            description = "Retrieve the current user's information.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved user information",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = UserInfoVo.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized access",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "621",
                            description = "LoginUser From Redis Get Fail",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    )
            }
    )
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public ResponseResult<UserInfoVo> getUserInfo() {

        return userService.getUserInfo() ;
    }

    @Operation(
            summary = "Update User Information",
            description = "Update the current user's information.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User information to be updated",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = UserInfoRequest.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully updated user information",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request parameters"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "613",
                            description = "User not found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    )
            }
    )
    @RequestMapping(value = "/userInfo", method = RequestMethod.PUT)
    public ResponseResult<Object> updateUserInfo(@Valid @RequestBody UserInfoRequest userInfoRequest) {

        return userService.updateUserInfo(userInfoRequest) ;
    }

    @Operation(
            summary = "Register New User",
            description = "Register a new user with the provided information.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User registration details",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = UserRegisterRequest.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Successfully registered new user",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid registration details"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "601",
                            description = "UserName has existed",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    )
            }
    )
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseResult<Object> register(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {

        return userService.register(userRegisterRequest) ;
    }

    @Operation(
            summary = "Forgot Password",
            description = "Send a verification code to the user's email for password reset.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User information for password reset",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = CheckInfoDto.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Verification code sent successfully",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid user information"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "613",
                            description = "User not found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "628",
                            description = "User relational information not match",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    )
            }
    )
    @PostMapping(value = "/password/forgot")
    public ResponseResult<Object> forgotPassword(@Valid @RequestBody CheckInfoDto checkInfoDto) {

        return userService.forgotPassword(checkInfoDto) ;
    }

    @Operation(
            summary = "Verify Code",
            description = "Verify the verification code sent to the user's email.",
            parameters = @Parameter(
                    name = "verificationCode",
                    description = "Verification code sent to the user's email",
                    required = true
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Verification code verified successfully",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid verification code"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "630",
                            description = "Verification is not passed",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "631",
                            description = "Verification code has expired",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    )
            }
    )
    @GetMapping(value = "/verifyCode")
    public ResponseResult<Object> verifyCode(@RequestParam("verificationCode") String verificationCode) {

        return userService.verifyCode(verificationCode) ;

    }

    @Operation(
            summary = "Reset Password",
            description = "Reset the user's password using the provided information.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Password reset details",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = PasswordResetRequest.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Password reset successfully",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid password reset details"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "613",
                            description = "User not found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "632",
                            description = "Reset password overtime",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "633",
                            description = "The verify sign of reset password is invalid",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    )
            }
    )
    @PostMapping(value = "/password/reset")
    public ResponseResult<Object> resetPassword(@Valid @RequestBody PasswordResetRequest passwordResetRequest) {

        return userService.resetPassword(passwordResetRequest) ;
    }
}
