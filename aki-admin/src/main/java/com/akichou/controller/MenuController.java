package com.akichou.controller;

import com.akichou.domain.ResponseResult;
import com.akichou.domain.dto.MenuAddDto;
import com.akichou.domain.dto.MenuEditDto;
import com.akichou.domain.vo.MenuInfoVo;
import com.akichou.domain.vo.MenuPageSelectVo;
import com.akichou.domain.vo.MenuTreeSelectVo;
import com.akichou.domain.vo.RoleMenuTreeSelectVo;
import com.akichou.exception.SystemException;
import com.akichou.service.MenuService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Blog Admin Menu Management Controller
 *
 * <p>
 * This controller manages admin operations related to menus.
 * It handles basic functionalities such as adding, listing, retrieving, editing, and deleting menus.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/24 Wed.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/menu")
@Tag(name = "Admin Menu Controller API", description = "Controller about handling menus")
public class MenuController {

    private final MenuService menuService;

    @Operation(
            summary = "List all menus",
            description = "List all menus based on menu name and status",
            parameters = {
                    @Parameter(name = "menuName", description = "Name of the menu"),
                    @Parameter(name = "status", description = "Status of the menu")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns a list of all menus",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = List.class,
                                            subTypes = MenuPageSelectVo.class
                                    )
                            )
                    )
            }
    )
    @GetMapping(value = "/list")
    public ResponseResult<List<MenuPageSelectVo>> listAllMenu(@RequestParam("menuName") String menuName,
                                                              @RequestParam("status") Integer status) {

        return menuService.listAllMenu(menuName, status) ;
    }

    @Operation(
            summary = "Add a new menu",
            description = "Add a new menu with the provided details",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO encapsulated menu info",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = MenuAddDto.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Menu successfully added",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request data"
                    )
            }
    )
    @PostMapping
    public ResponseResult<Object> addMenu(@Valid @RequestBody MenuAddDto menuAddDto) {

        return menuService.addMenu(menuAddDto);
    }

    @Operation(
            summary = "Get menu information",
            description = "Retrieve menu information based on the provided menu ID",
            parameters = {
                    @Parameter(name = "menuId", description = "ID of the menu to retrieve", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns the menu information",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = MenuInfoVo.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "625",
                            description = "Menu not found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    )
            }
    )
    @GetMapping(value = "/{menuId}")
    public ResponseResult<MenuInfoVo> getMenu(@PathVariable("menuId") Long menuId) {

        return menuService.getMenu(menuId) ;
    }

    @Operation(
            summary = "Edit a menu",
            description = "Edit an existing menu with the provided details",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO encapsulated modifying menu info",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = MenuEditDto.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Menu successfully edited",
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
                            responseCode = "625",
                            description = "Menu not found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    )
            }
    )
    @PutMapping
    public ResponseResult<Object> editMenu(@Valid @RequestBody MenuEditDto menuEditDto) {

        if(menuEditDto.getId().equals(menuEditDto.getParentId())) {
            return ResponseResult.errorResult(500, "Modify Menu : " + menuEditDto.getMenuName() + " Failed ! Super Menu can't choose itself !") ;
        }

        return menuService.editMenu(menuEditDto) ;
    }

    @Operation(
            summary = "Delete a menu",
            description = "Delete a menu based on the provided menu ID",
            parameters = {
                    @Parameter(name = "menuId", description = "ID of the menu to be deleted", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Menu successfully deleted",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "625",
                            description = "Menu not found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    )
            }
    )
    @DeleteMapping(value = "/{menuId}")
    public ResponseResult<Object> deleteMenu(@PathVariable("menuId") Long menuId) {

        return menuService.deleteMenu(menuId) ;
    }

    @Operation(
            summary = "Get menu tree",
            description = "Retrieve a tree structure of menus for selection",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns the menu tree structure",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = List.class,
                                            subTypes = MenuTreeSelectVo.class
                                    )
                            )
                    )
            }
    )
    @GetMapping(value = "/treeSelect")
    public ResponseResult<List<MenuTreeSelectVo>> treeSelect() {

        return menuService.treeSelect() ;
    }

    @Operation(
            summary = "Get role menu tree",
            description = "Retrieve a tree structure of menus based on the provided role ID for selection",
            parameters = {
                    @Parameter(name = "roleId", description = "ID of the role", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns the role menu tree structure",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = RoleMenuTreeSelectVo.class
                                    )
                            )
                    )
            }
    )
    @GetMapping(value = "/roleMenuTreeSelect/{roleId}")
    public ResponseResult<RoleMenuTreeSelectVo> roleMenuTreeSelect(@PathVariable("roleId") Long roleId) {

        return menuService.roleMenuTreeSelect(roleId) ;
    }
}
