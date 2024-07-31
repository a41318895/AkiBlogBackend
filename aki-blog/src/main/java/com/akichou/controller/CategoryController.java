package com.akichou.controller;

import com.akichou.domain.ResponseResult;
import com.akichou.domain.vo.CategoryVo;
import com.akichou.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Blog Front-Stage Category Controller
 *
 * <p>
 * This controller manages front-stage operations related to categories.
 * It handles the retrieval of category lists, which can be used for filtering articles or displaying category options.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/24 Wed.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
@Tag(name = "Category Controller API", description = "Front-Stage Controller about handling categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(
            summary = "Get Category List",
            description = "Retrieve a list of categories available in the system",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved category list",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = List.class,
                                            subTypes = CategoryVo.class
                                    )
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
    @GetMapping(value = "/getCategoryList")
    public ResponseResult<List<CategoryVo>> getCategoryList() {

        ResponseResult<List<CategoryVo>> listResponseResult = categoryService.listAllCategories();

        return listResponseResult ;
    }
}
