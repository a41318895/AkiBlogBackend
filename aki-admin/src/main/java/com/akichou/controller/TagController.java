package com.akichou.controller;

import com.akichou.customEnum.AppHttpCodeEnum;
import com.akichou.domain.ResponseResult;
import com.akichou.domain.dto.TagAddDto;
import com.akichou.domain.dto.TagEditDto;
import com.akichou.domain.dto.TagSelectConditionsDto;
import com.akichou.domain.entity.Tag;
import com.akichou.domain.vo.*;
import com.akichou.exception.SystemException;
import com.akichou.service.TagService;
import com.akichou.util.BeanCopyUtil;
import com.akichou.util.WebUtils;

import com.alibaba.excel.EasyExcel;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import java.util.List;

/**
 * Blog Admin Tag Management Controller
 *
 * <p>
 * This controller manages admin operations related to tags.
 * It handles functionalities such as adding, listing, retrieving, editing, and deleting tags.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/24 Wed.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/content/tag")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Admin Tag Controller API", description = "Controller about handling tags")
public class TagController {

    private final TagService tagService ;

    @Operation(
            summary = "List all tags",
            description = "Retrieve a list of all tags",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns a list of all tags",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = List.class,
                                            subTypes = TagVo.class
                                    )
                            )
                    )
            }
    )
    @GetMapping(value = "/listAllTag")
    public ResponseResult<List<TagVo>> listAllTags() {

        return tagService.listAllTags() ;
    }

    @Operation(
            summary = "List tags",
            description = "List tags based on pagination and filtering conditions",
            parameters = {
                    @Parameter(name = "pageNum", description = "Current page number", required = true),
                    @Parameter(name = "pageSize", description = "Number of records per page", required = true),
                    @Parameter(name = "tagListDto", description = "DTO encapsulated tag list conditions")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns a paginated list of tags",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = PageVo.class,
                                            subTypes = TagPageSelectVo.class
                                    )
                            )
                    )
            }
    )
    @GetMapping(value = "/list")
    public ResponseResult<PageVo<TagPageSelectVo>> selectTags(@RequestParam(name = "pageNum") Integer pageNum,
                                                              @RequestParam(name = "pageSize") Integer pageSize,
                                                              TagSelectConditionsDto tagSelectConditionsDto) {

        return tagService.selectTags(pageNum, pageSize, tagSelectConditionsDto) ;
    }

    @Operation(
            summary = "Add a new tag",
            description = "Add a new tag with the provided details",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO encapsulated tag info",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = TagAddDto.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tag successfully added",
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
                            responseCode = "614",
                            description = "Tag name and remarks both can't be BLANK !",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    )
            }
    )
    @PostMapping
    public ResponseResult<Object> addTag(@Valid @RequestBody TagAddDto tagAddDto) {

        return tagService.addTag(tagAddDto) ;
    }

    @Operation(
            summary = "Get tag information",
            description = "Retrieve tag information based on the provided tag ID",
            parameters = {
                    @Parameter(name = "id", description = "ID of the tag to retrieve", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns the tag information",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = TagInfoVo.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "615",
                            description = "Tag not found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    )
            }
    )
    @GetMapping(value = "/{id}")
    public ResponseResult<TagInfoVo> getTag(@PathVariable("id") Long tagId) {

        return tagService.getTag(tagId) ;
    }

    @Operation(
            summary = "Edit a tag",
            description = "Edit an existing tag with the provided details",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO encapsulated modifying tag info",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = TagEditDto.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tag successfully edited",
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
                            responseCode = "614",
                            description = "Tag name and remarks both can't be BLANK !",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "615",
                            description = "Tag not found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    )
            }
    )
    @PutMapping
    public ResponseResult<Object> editTag(@Valid @RequestBody TagEditDto tagEditDto) {

        return tagService.editTag(tagEditDto) ;
    }

    @Operation(
            summary = "Delete a tag",
            description = "Delete a tag based on the provided tag ID",
            parameters = {
                    @Parameter(name = "id", description = "ID of the tag to be deleted", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tag successfully deleted",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "615",
                            description = "Tag not found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class
                                    )
                            )
                    )
            }
    )
    @DeleteMapping(value = "/{id}")
    public ResponseResult<Object> deleteTag(@PathVariable("id") Long tagId) {

        return tagService.deleteTag(tagId) ;
    }

    @Operation(
            summary = "Export tags to an Excel file",
            description = "Export all tags to an Excel file",
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
    @PreAuthorize("@ps.hasPermission('content:tag:export')")
    @GetMapping(value = "/export")
    public void export(HttpServletResponse response) throws IOException {
        try {
            WebUtils.setDownLoadHeader("標籤導出.xlsx", response);

            List<Tag> tags = tagService.listExistTags();
            List<ExcelTagVo> excelTagVos = BeanCopyUtil.copyBeanList(tags, ExcelTagVo.class);

            EasyExcel.write(response.getOutputStream(), ExcelTagVo.class).autoCloseStream(Boolean.FALSE).sheet("標籤導出")
                    .doWrite(excelTagVos);

        } catch (Exception e) {

            ResponseResult<Object> result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR) ;

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(result) ;

            WebUtils.renderString(response, jsonString);
        }
    }
}
