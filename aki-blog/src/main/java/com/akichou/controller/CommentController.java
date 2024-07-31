package com.akichou.controller;

import com.akichou.domain.ResponseResult;
import com.akichou.domain.dto.CommentAddDto;
import com.akichou.domain.vo.CommentVo;
import com.akichou.domain.vo.PageVo;
import com.akichou.exception.SystemException;
import com.akichou.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.akichou.constant.SystemConstants.ARTICLE_COMMENT;
import static com.akichou.constant.SystemConstants.LINK_COMMENT;

/**
 * Blog Front-Stage Comment Controller
 *
 * <p>
 * This controller manages front-stage operations related to comments.
 * It handles functionalities such as listing comments for articles and links, and adding new comments.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/24 Wed.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@Tag(name = "Comment Controller API", description = "Front-Stage Controller about handling comments")
public class CommentController {

    private final CommentService commentService;

    @Operation(
            summary = "Get Comment List",
            description = "Retrieve a paginated list of comments for a specific article",
            parameters = {
                    @Parameter(name = "articleId", description = "ID of the article", required = true),
                    @Parameter(name = "pageNum", description = "Page number", required = true),
                    @Parameter(name = "pageSize", description = "Page size", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved comment list",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = PageVo.class,
                                            subTypes = CommentVo.class
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
                                            implementation = ResponseResult.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "619",
                            description = "InValid Comment Type",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class)
                            )
                    )
            }
    )
    @GetMapping(value = "/commentList")
    public ResponseResult<PageVo<CommentVo>> getCommentList(
                                @RequestParam(name = "articleId") Long articleId,
                                @RequestParam(name = "pageNum") Integer pageNum,
                                @RequestParam(name = "pageSize") Integer pageSize){

        return commentService.getCommentList(ARTICLE_COMMENT, articleId, pageNum, pageSize) ;
    }

    @Operation(
            summary = "Add Comment",
            description = "Add a new comment",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO containing the comment details",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CommentAddDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully added the comment",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseResult.class)
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
                                    schema = @Schema(implementation = ResponseResult.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "606",
                            description = "Comment content can't be BLANK !",
                            content = @Content(
                                    schema = @Schema(implementation = SystemException.class)
                            )
                    )
            }
    )
    @PostMapping
    public ResponseResult<Object> addComment(@Valid @RequestBody CommentAddDto commentAddDto){

        return commentService.addComment(commentAddDto) ;
    }

    @Operation(
            summary = "Get Link Comment List",
            description = "Retrieve a paginated list of comments for links",
            parameters = {
                    @Parameter(name = "pageNum", description = "Page number", required = true),
                    @Parameter(name = "pageSize", description = "Page size", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved link comment list",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = PageVo.class,
                                            subTypes = CommentVo.class
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
                                    schema = @Schema(implementation = ResponseResult.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "619",
                            description = "InValid Comment Type",
                            content = @Content(
                                    schema = @Schema(implementation = SystemException.class)
                            )
                    )
            }
    )
    @GetMapping(value = "/linkCommentList")
    public ResponseResult<PageVo<CommentVo>> getLinkCommentList(
                                @RequestParam(name = "pageNum") Integer pageNum,
                                @RequestParam(name = "pageSize") Integer pageSize) {

        return commentService.getCommentList(LINK_COMMENT,null, pageNum, pageSize) ;
    }
}
