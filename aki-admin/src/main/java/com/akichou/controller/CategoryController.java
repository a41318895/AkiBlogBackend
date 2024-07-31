package com.akichou.controller;

import com.akichou.customEnum.AppHttpCodeEnum;
import com.akichou.domain.ResponseResult;
import com.akichou.domain.dto.CategoryAddDto;
import com.akichou.domain.dto.CategorySelectConditionsDto;
import com.akichou.domain.dto.CategoryEditDto;
import com.akichou.domain.entity.Category;
import com.akichou.domain.vo.*;
import com.akichou.exception.SystemException;
import com.akichou.service.CategoryService;
import com.akichou.util.BeanCopyUtil;
import com.akichou.util.JsonUtil;
import com.akichou.util.WebUtils;

import com.alibaba.excel.EasyExcel;

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
 * Blog Admin Category Management Controller
 *
 * <p>
 * This controller manages admin operations related to categories.
 * It handles basic functionalities such as adding, listing, retrieving, editing, deleting, and exporting articles.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/24 Wed.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/content/category")
@Tag(name = "Admin Category Controller API", description = "Controller about handling categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(
            summary = "List all article categories",
            description = "Listing all available categories for selecting when writing an article",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns a list of categories",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = List.class,
                                            subTypes = CategoryVo.class
                                    ))
                    )
            }
    )
    @GetMapping(value = "/listAllCategory")
    public ResponseResult<List<CategoryVo>> listAllCategories() {

        return categoryService.listAllCategories() ;
    }

    @Operation(
            summary = "Query categories",
            description = "Retrieve a list of categories based on provided page number, page size, and query conditions",
            parameters = {
                    @Parameter(name = "pageNum", description = "Page number"),
                    @Parameter(name = "pageSize", description = "Number of items per page"),
                    @Parameter(name = "categoryListConditionsDto", description = "Query conditions")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns the queried list of categories",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = PageVo.class,
                                            subTypes = CategoryPageSelectVo.class ))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request data"
                    )
            }
    )
    @GetMapping(value = "/list")
    public ResponseResult<PageVo<CategoryPageSelectVo>> selectCategories(@RequestParam("pageNum") Integer pageNum,
                                                                         @RequestParam("pageSize") Integer pageSize,
                                                                         CategorySelectConditionsDto categorySelectConditionsDto) {

        return categoryService.selectCategories(pageNum, pageSize, categorySelectConditionsDto) ;
    }

    @Operation(
            summary = "Add a new category",
            description = "Add a new category with the provided attributes",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO encapsulated attributes of the new category",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = CategoryAddDto.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns a message indicating the successful addition",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request data"
                    )
            }
    )
    @PostMapping
    public ResponseResult<Object> addCategory(@Valid @RequestBody CategoryAddDto categoryAddDto) {

        return categoryService.addCategory(categoryAddDto) ;
    }

    @Operation(
            summary = "Get category information",
            description = "Retrieve information of a category based on the provided category ID",
            parameters = {
                    @Parameter(name = "categoryId", description = "ID of the category to retrieve")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns information of the retrieved category",
                            content = @Content(
                                    schema = @Schema(implementation =
                                            CategoryInfoVo.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "616",
                            description = "Category not found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class))
                    )
            }
    )
    @GetMapping(value = "/{categoryId}")
    public ResponseResult<CategoryInfoVo> getCategory(@PathVariable("categoryId") Long categoryId) {

        return categoryService.getCategory(categoryId) ;
    }

    @Operation(
            summary = "Edit a category",
            description = "Update category information based on the provided new attributes",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO encapsulated updated category information",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = CategoryEditDto.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns a message indicating the successful update",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request data"
                    ),
                    @ApiResponse(
                            responseCode = "616",
                            description = "Category not found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class))
                    )
            }
    )
    @PutMapping
    public ResponseResult<Object> editCategory(@Valid @RequestBody CategoryEditDto categoryEditDto) {

        return categoryService.editCategory(categoryEditDto) ;
    }

    @Operation(
            summary = "Delete a category",
            description = "Delete a category based on the provided category ID",
            parameters = {
                    @Parameter(name = "categoryId", description = "ID of the category to delete")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns a message indicating the successful deletion",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class))
                    ),
                    @ApiResponse(
                            responseCode = "616",
                            description = "Category not found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class))
                    )
            }
    )
    @DeleteMapping(value = "/{categoryId}")
    public ResponseResult<Object> deleteCategory(@PathVariable("categoryId") Long categoryId) {

        return categoryService.deleteCategory(categoryId) ;

    }

    @Operation(
            summary = "Export categories",
            description = "Export category data to Excel upon clicking the export button in the admin category management interface",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Categories exported successfully"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class))
                    )
            }
    )
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping(value = "/export")
    public void export(HttpServletResponse response) throws IOException {

        try {

            // Set Attributes for easy excel downloading
            WebUtils.setDownLoadHeader("分類導出.xlsx", response);

            // Vo Encapsulation
            List<Category> categories = categoryService.listExistCategories();
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtil.copyBeanList(categories, ExcelCategoryVo.class);

            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class)
                    .autoCloseStream(Boolean.FALSE)
                    .sheet("分類導出")
                    .doWrite(excelCategoryVos);
        } catch (Exception e) {

            ResponseResult<Object> result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR) ;
            String jsonString = JsonUtil.convertToJsonString(result);

            WebUtils.renderString(response, jsonString);
        }
    }
}
