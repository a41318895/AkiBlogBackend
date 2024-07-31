package com.akichou.controller;

import com.akichou.customEnum.AppHttpCodeEnum;
import com.akichou.domain.ResponseResult;
import com.akichou.domain.dto.RoleAddDto;
import com.akichou.domain.dto.RoleChangeStatusDto;
import com.akichou.domain.dto.RoleEditDto;
import com.akichou.domain.dto.RoleSelectConditionsDto;
import com.akichou.domain.entity.Role;
import com.akichou.domain.vo.*;
import com.akichou.exception.SystemException;
import com.akichou.service.RoleService;
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
 * Blog Admin Role Management Controller
 *
 * <p>
 * This controller manages admin operations related to roles.
 * It handles functionalities such as adding, listing, retrieving, editing, and deleting roles.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/24 Wed.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/role")
@Tag(name = "Admin Role Controller API", description = "Controller about handling roles")
public class RoleController {

    private final RoleService roleService;

    @Operation(
            summary = "List all roles",
            description = "Retrieve a list of all roles",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns a list of all roles",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = List.class,
                                            subTypes = RoleListVo.class
                                    )
                            )
                    )
            }
    )
    @GetMapping(value = "/listAllRole")
    public ResponseResult<List<RoleListVo>> listAllRoles() {

        return roleService.listAllRoles() ;
    }

    @Operation(
            summary = "List roles",
            description = "List roles based on pagination and filtering conditions",
            parameters = {
                    @Parameter(name = "pageNum", description = "Current page number", required = true),
                    @Parameter(name = "pageSize", description = "Number of records per page", required = true),
                    @Parameter(name = "roleListConditionsDto", description = "DTO encapsulated role list conditions")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns a paginated list of roles",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = PageVo.class,
                                            subTypes = RolePageSelectVo.class
                                    )
                            )
                    )
            }
    )
    @GetMapping(value = "/list")
    public ResponseResult<PageVo<RolePageSelectVo>> selectRoles(@RequestParam("pageNum") Integer pageNum,
                                                      @RequestParam("pageSize") Integer pageSize,
                                                      RoleSelectConditionsDto roleSelectConditionsDto) {

        return roleService.selectRoles(pageNum, pageSize, roleSelectConditionsDto) ;
    }

    @Operation(
            summary = "Add a new role",
            description = "Add a new role with the provided details",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO encapsulated role info",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = RoleAddDto.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Role successfully added",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request data"
                    ),
                    @ApiResponse(
                            responseCode = "620",
                            description = "Some menus not found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    )
            }
    )
    @PostMapping
    public ResponseResult<Object> addRole(@Valid @RequestBody RoleAddDto roleAddDto) {

        return roleService.addRole(roleAddDto) ;
    }

    @Operation(
            summary = "Get role information",
            description = "Retrieve role information based on the provided role ID",
            parameters = {
                    @Parameter(name = "roleId", description = "ID of the role to retrieve", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns the role information",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = RoleInfoVo.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "626",
                            description = "Role not found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    )
            }
    )
    @GetMapping(value = "/{roleId}")
    public ResponseResult<RoleInfoVo> getRoleInfo(@PathVariable("roleId") Long roleId) {

        return roleService.getRoleInfo(roleId) ;
    }

    @Operation(
            summary = "Edit a role",
            description = "Edit an existing role with the provided details",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO encapsulated modifying role info",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = RoleEditDto.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Role successfully edited",
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
                            responseCode = "626",
                            description = "Role not found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    )
            }
    )
    @PutMapping
    public ResponseResult<Object> editRole(@Valid @RequestBody RoleEditDto roleEditDto) {

        return roleService.editRole(roleEditDto) ;
    }

    @Operation(
            summary = "Delete a role",
            description = "Delete a role based on the provided role ID",
            parameters = {
                    @Parameter(name = "roleId", description = "ID of the role to be deleted", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Role successfully deleted",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "626",
                            description = "Role not found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    )
            }
    )
    @DeleteMapping(value = "/{roleId}")
    public ResponseResult<Object> deleteRole(@PathVariable("roleId") Long roleId) {

        return roleService.deleteRole(roleId) ;
    }

    @Operation(
            summary = "Change role status",
            description = "Change the status of a role with the provided details",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO encapsulated role status change info",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = RoleChangeStatusDto.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Role status successfully changed",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request data"
                    ),
                    @ApiResponse(
                            responseCode = "626",
                            description = "Role not found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    )
            }
    )
    @PutMapping(value = "/changeStatus")
    public ResponseResult<Object> changeRoleStatus(@Valid @RequestBody RoleChangeStatusDto roleChangeStatusDto) {

        return roleService.changeRoleStatus(roleChangeStatusDto) ;
    }

    @Operation(
            summary = "Export roles to an Excel file",
            description = "Export all roles to an Excel file",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Excel file successfully created and downloaded"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    )
            }
    )
    @PreAuthorize("@ps.hasPermission('system:role:export')")
    @GetMapping(value = "/export")
    public void export(HttpServletResponse response) throws IOException {
        try {
            WebUtils.setDownLoadHeader("角色導出.xlsx", response);

            List<Role> roles = roleService.listExistRoles();
            List<ExcelRoleVo> excelRoleVos = BeanCopyUtil.copyBeanList(roles, ExcelRoleVo.class);

            EasyExcel.write(response.getOutputStream(), ExcelRoleVo.class).autoCloseStream(Boolean.FALSE).sheet("角色導出")
                    .doWrite(excelRoleVos);

        } catch (Exception e) {

            ResponseResult<Object> result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR) ;

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(result) ;

            WebUtils.renderString(response, jsonString);
        }
    }
}
