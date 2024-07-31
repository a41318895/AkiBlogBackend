package com.akichou.controller;

import com.akichou.annotation.SystemLog;
import com.akichou.domain.ResponseResult;
import com.akichou.domain.vo.ArticleDetailVo;
import com.akichou.domain.vo.ArticleVo;
import com.akichou.domain.vo.HotArticleVo;
import com.akichou.domain.vo.PageVo;
import com.akichou.exception.SystemException;
import com.akichou.service.ArticleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Blog Front-Stage Article Management Controller
 *
 * <p>
 * This controller manages front-stage operations related to articles.
 * It handles basic functionalities such as adding, listing, retrieving, editing, and deleting articles.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/24 Wed.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
@Tag(name = "Article Controller API", description = "Front-Stage Controller about handling articles")
public class ArticleController {

    private final ArticleService articleService;

    @Operation(
            summary = "Get hot article list",
            description = "Retrieve a list of hot articles",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved hot articles list",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = List.class,
                                            subTypes = HotArticleVo.class
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
                    )
            }
    )
    @GetMapping(value = "/hotArticleList")
    @SystemLog(businessName = "獲取熱門文章列表")
    public ResponseResult<List<HotArticleVo>> getHotArticleList() {

        return articleService.getHotArticleList() ;
    }

    @Operation(
            summary = "Get article list",
            description = "Retrieve a paginated list of articles. Optionally filter by category.",
            parameters = {
                    @Parameter(name = "pageNum", description = "Page number", required = true),
                    @Parameter(name = "pageSize", description = "Page size", required = true),
                    @Parameter(name = "categoryId", description = "Category ID to filter articles by")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved article list",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = PageVo.class,
                                            subTypes = ArticleVo.class
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
                    )
            }
    )
    @GetMapping(value = "/articleList")
    public ResponseResult<PageVo<ArticleVo>> getArticleList(
                            @RequestParam(name = "pageNum") Integer pageNum,
                            @RequestParam(name = "pageSize") Integer pageSize,
                            @RequestParam(name = "categoryId", required = false) Long categoryId) {

        return articleService.getArticleList(pageNum, pageSize, categoryId);
    }

    @Operation(
            summary = "Get article detail",
            description = "Retrieve detailed information of an article by its ID",
            parameters = {
                    @Parameter(name = "articleId", description = "Article ID", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved article detail",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ArticleDetailVo.class
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
                            responseCode = "616",
                            description = "Category Not Found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "617",
                            description = "Article Not Found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    )
            }
    )
    @GetMapping(value = "/{articleId}")
    public ResponseResult<ArticleDetailVo> getArticleDetail(@PathVariable("articleId") Long articleId) {

        return articleService.getArticleDetail(articleId) ;
    }

    @Operation(
            summary = "Update article view count",
            description = "Increment the view count of an article by its ID",
            parameters = {
                    @Parameter(name = "articleId", description = "Article ID", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully updated view count",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Article not found"
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
    @PutMapping(value = "/updateViewCount/{articleId}")
    public ResponseResult<Object> updateViewCount(@PathVariable("articleId") Long articleId) {

        return articleService.updateViewCount(articleId) ;
    }
}
