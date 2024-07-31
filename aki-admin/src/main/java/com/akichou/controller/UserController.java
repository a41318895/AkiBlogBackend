package com.akichou.controller;

import com.akichou.customEnum.AppHttpCodeEnum;
import com.akichou.domain.ResponseResult;
import com.akichou.domain.dto.UserAddDto;
import com.akichou.domain.dto.UserChangeStatusDto;
import com.akichou.domain.dto.UserEditDto;
import com.akichou.domain.dto.UserSelectConditionsDto;
import com.akichou.domain.entity.User;
import com.akichou.domain.vo.*;
import com.akichou.exception.SystemException;
import com.akichou.service.UserService;
import com.akichou.util.BeanCopyUtil;
import com.akichou.util.WebUtils;

import com.alibaba.excel.EasyExcel;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import java.util.List;

/**
 * Blog Admin User Management Controller
 *
 * <p>
 * This controller manages admin operations related to user management.
 * It handles functionalities such as listing, adding, deleting, editing, and exporting user information.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/24 Wed.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/user")
@Tag(name = "Admin User Controller API", description = "Controller about handling users")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "List users",
            description = "Get a paginated list of users based on the provided conditions",
            parameters = {
                    @Parameter(name = "pageNum", description = "Page number", required = true),
                    @Parameter(name = "pageSize", description = "Page size", required = true),
                    @Parameter(name = "userListDto", description = "DTO encapsulated user list conditions")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved user list",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = PageVo.class,
                                            subTypes = UserPageSelectVo.class
                                    )
                            )
                    )
            }
    )
    @GetMapping(value = "/list")
    public ResponseResult<PageVo<UserPageSelectVo>> selectUsers(@RequestParam("pageNum") Integer pageNum,
                                                                @RequestParam("pageSize") Integer pageSize,
                                                                UserSelectConditionsDto userSelectConditionsDto) {

        return userService.selectUsers(pageNum, pageSize, userSelectConditionsDto) ;
    }

    @Operation(
            summary = "Add a new user",
            description = "Add a new user with the provided information",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User details to add",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = UserAddDto.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully added user",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data"
                    ),
                    @ApiResponse(
                            responseCode = "623",
                            description = "Some roles not found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    )
            }
    )
    @PostMapping
    public ResponseResult<Object> addUser(@Valid @RequestBody UserAddDto userAddDto) {

        return userService.addUser(userAddDto) ;
    }


    @Operation(
            summary = "Get user information",
            description = "Get detailed information of a user by their ID",
            parameters = {
                    @Parameter(name = "userId", description = "User ID", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved user information",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = UserInfoAndRoleListVo.class
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
    @GetMapping(value = "/{userId}")
    public ResponseResult<UserInfoAndRoleListVo> getUser(@PathVariable("userId") Long userId) {

        return userService.getUser(userId) ;
    }

    @Operation(
            summary = "Edit user information",
            description = "Edit user information with the provided details",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User details to edit",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = UserEditDto.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully edited user",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data"
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
    @PutMapping
    public ResponseResult<Object> editUser(@Valid @RequestBody UserEditDto userEditDto) {

        return userService.editUser(userEditDto) ;
    }

    @Operation(
            summary = "Delete a user",
            description = "Delete a user by their ID",
            parameters = {
                    @Parameter(name = "userId", description = "User ID", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully deleted user",
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
    @DeleteMapping(value = "/{userId}")
    public ResponseResult<Object> deleteUser(@PathVariable("userId") Long userId) {

        return userService.deleteUser(userId) ;
    }

    @Operation(
            summary = "Change user status",
            description = "Change the status of a user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User status details to change",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = UserChangeStatusDto.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully changed user status",
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
    @PutMapping(value = "/changeStatus")
    public ResponseResult<Object> changeUserStatus(@Valid @RequestBody UserChangeStatusDto userChangeStatusDto) {

        return userService.changeUserStatus(userChangeStatusDto) ;
    }

    @Operation(
            summary = "Export users",
            description = "Export the list of all users to an Excel file",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully exported user list",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error during export",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    )
            }
    )
    @PreAuthorize("@ps.hasPermission('system:user:export')")
    @GetMapping(value = "/export")
    public void export(HttpServletResponse response) throws IOException {
        try {
            WebUtils.setDownLoadHeader("用戶導出.xlsx", response);

            List<User> users = userService.listExistUsers();
            List<ExcelUserVo> excelUserVos = BeanCopyUtil.copyBeanList(users, ExcelUserVo.class);

            EasyExcel.write(response.getOutputStream(), ExcelUserVo.class).autoCloseStream(Boolean.FALSE).sheet("用戶導出")
                    .doWrite(excelUserVos);

        } catch (Exception e) {

            ResponseResult<Object> result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR) ;

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(result) ;

            WebUtils.renderString(response, jsonString);
        }
    }
}
