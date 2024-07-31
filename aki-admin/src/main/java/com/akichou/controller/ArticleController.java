package com.akichou.controller;

import com.akichou.customEnum.AppHttpCodeEnum;
import com.akichou.domain.ResponseResult;
import com.akichou.domain.dto.ArticleAddDto;
import com.akichou.domain.dto.ArticleEditDto;
import com.akichou.domain.dto.ArticleSelectConditionsDto;
import com.akichou.domain.entity.Article;
import com.akichou.domain.vo.*;
import com.akichou.exception.SystemException;
import com.akichou.service.ArticleService;
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

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Blog Admin Article Management Controller
 *
 * <p>
 * This controller manages admin operations related to articles.
 * It handles basic functionalities such as adding, listing, retrieving, editing, and deleting articles.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/24 Wed.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/content/article")
@Tag(name = "Admin Article Controller API", description = "Controller about handling articles")
public class ArticleController {

    private final ArticleService articleService;

    @Operation(
            summary = "List articles",
            description = "Listing corresponding articles via page number, page size, and other conditions in dto",
            parameters = {
                    @Parameter(name = "pageNum", description = "Select page number", required = true),
                    @Parameter(name = "pageSize", description = "Select page size", required = true),
                    @Parameter(name = "articleListConditionsDto", description = "Filter conditions DTO")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns article data after filtering",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = PageVo.class,
                                            subTypes = ArticlePageSelectVo.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request parameters"
                    )
            }
    )
    @GetMapping(value = "/list")
    public ResponseResult<PageVo<ArticlePageSelectVo>> selectArticles(
            @RequestParam(name = "pageNum") Integer pageNum,
            @RequestParam(name = "pageSize") Integer pageSize,
            ArticleSelectConditionsDto articleSelectConditionsDto) {

        return articleService.selectArticles(pageNum, pageSize, articleSelectConditionsDto) ;
    }

    @Operation(
            summary = "Write new article",
            description = "Write a new article via sent DTO encapsulated article info in admin CMS",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO encapsulated article info",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = ArticleAddDto.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Return successful message after writing a new article",
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
                            responseCode = "618",
                            description = "Some tags not found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    )
            }
    )
    @PostMapping
    public ResponseResult<Object> addArticle(@Valid @RequestBody ArticleAddDto articleAddDto) {

        return articleService.addArticle(articleAddDto) ;
    }

    @Operation(
            summary = "Get article info",
            description = "Get article related info and binding tags via article id",
            parameters = {
                    @Parameter(name = "articleId", description = "Target article ID", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns corresponding article info",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ArticleInfoVo.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "617",
                            description = "Article not found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    )
            }
    )
    @GetMapping(value = "/{articleId}")
    public ResponseResult<ArticleInfoVo> getArticle(@PathVariable("articleId") Long articleId) {

        return articleService.getArticle(articleId) ;
    }

    @Operation(
            summary = "Edit article",
            description = "Modify article info via requested dto attributes",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO encapsulated modifying article info",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = ArticleEditDto.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns the successful message of article editing",
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
                            responseCode = "617",
                            description = "Article not found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "618",
                            description = "Some tags not found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    )
            }
    )
    @PutMapping
    public ResponseResult<Object> editArticle(@Valid @RequestBody ArticleEditDto articleEditDto) {

        return articleService.editArticle(articleEditDto) ;
    }

    @Operation(
            summary = "Delete article",
            description = "Deleting the corresponding article via article id",
            parameters = {
                    @Parameter(name = "articleId", description = "ID of the article to be deleted", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Article successfully deleted",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class))
                    ),
                    @ApiResponse(
                            responseCode = "617",
                            description = "Article not found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class))
                    )
            }
    )
    @DeleteMapping(value = "/{articleId}")
    public ResponseResult<Object> deleteArticle(@PathVariable("articleId") Long articleId) {

        return articleService.deleteArticle(articleId) ;
    }

    @Operation(
            summary = "Import articles",
            description = "Press the import button in the front, it will import articles' info as excel",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Articles exported successfully"
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
    @PreAuthorize("@ps.hasPermission('content:article:export')")
    @GetMapping(value = "/export")
    public void export(HttpServletResponse httpServletResponse) throws IOException {

        try {

            // Set Attributes for easy excel downloading
            WebUtils.setDownLoadHeader("文章導出.xlsx", httpServletResponse);

            // Vo Encapsulation
            List<Article> articles = articleService.listExistArticles();
            List<ExcelArticleVo> excelArticleVos = BeanCopyUtil.copyBeanList(articles, ExcelArticleVo.class);

            EasyExcel.write(httpServletResponse.getOutputStream(), ExcelArticleVo.class)
                     .autoCloseStream(Boolean.FALSE)
                     .sheet("文章導出")
                     .doWrite(excelArticleVos);
        } catch (Exception e) {

            ResponseResult<Object> result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR) ;
            String jsonString = JsonUtil.convertToJsonString(result) ;

            // render error message to client side
            WebUtils.renderString(httpServletResponse, jsonString) ;
        }
    }
}
