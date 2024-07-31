package com.akichou.controller;

import com.akichou.customEnum.AppHttpCodeEnum;
import com.akichou.domain.ResponseResult;
import com.akichou.domain.dto.LinkAddDto;
import com.akichou.domain.dto.LinkEditDto;
import com.akichou.domain.dto.LinkChangeStatusDto;
import com.akichou.domain.dto.LinkSelectConditionsDto;
import com.akichou.domain.entity.Link;
import com.akichou.domain.vo.*;
import com.akichou.exception.SystemException;
import com.akichou.service.LinkService;
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
 * Blog Admin Link Management Controller
 *
 * <p>
 * This controller manages admin operations related to links.
 * It handles basic functionalities such as adding, listing, retrieving, editing, deleting, and exporting links.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/24 Wed.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/content/link")
@Tag(name = "Admin Link Controller API", description = "Controller about handling links")
public class LinkController {

    private final LinkService linkService;

    @Operation(
            summary = "List all links",
            description = "Listing all available links",
            parameters = {
                    @Parameter(name = "pageNum", description = "Select page number", required = true),
                    @Parameter(name = "pageSize", description = "Select page size", required = true),
                    @Parameter(name = "linkListConditionsDto", description = "Filter conditions DTO")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns link data after filtering",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = PageVo.class,
                                            subTypes = LinkPageSelectVo.class
                                    ))
                    )
            }
    )
    @GetMapping(value = "/list")
    public ResponseResult<PageVo<LinkPageSelectVo>> selectLinks(@RequestParam("pageNum") Integer pageNum,
                                                                @RequestParam("pageSize") Integer pageSize,
                                                                LinkSelectConditionsDto linkSelectConditionsDto) {
        return linkService.selectLinks(pageNum, pageSize, linkSelectConditionsDto) ;
    }

    @Operation(
            summary = "Add a new link",
            description = "Add a new link with the provided attributes",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO encapsulated attributes of the new link",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = LinkAddDto.class
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
    public ResponseResult<Object> addLink(@Valid @RequestBody LinkAddDto linkAddDto) {

        return linkService.addLink(linkAddDto) ;
    }

    @Operation(
            summary = "Get link information",
            description = "Retrieve information of a link based on the provided link ID",
            parameters = {
                    @Parameter(name = "linkId", description = "ID of the link to retrieve")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns information of the retrieved link",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = LinkInfoVo.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "624",
                            description = "Link not found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class))
                    )
            }
    )
    @GetMapping(value = "/{linkId}")
    public ResponseResult<LinkInfoVo> getLinkById(@PathVariable("linkId") Long linkId) {

        return linkService.getLinkById(linkId) ;
    }

    @Operation(
            summary = "Edit a link",
            description = "Update link information based on the provided new attributes",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO encapsulated updated link information",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = LinkEditDto.class
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
                            responseCode = "624",
                            description = "Link not found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class))
                    )
            }
    )
    @PutMapping
    public ResponseResult<Object> editLink(@Valid @RequestBody LinkEditDto linkEditDto) {

        return linkService.editLink(linkEditDto) ;
    }

    @Operation(
            summary = "Delete a link",
            description = "Delete a link based on the provided link ID",
            parameters = {
                    @Parameter(name = "linkId", description = "ID of the link to delete")
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
                            responseCode = "624",
                            description = "Link not found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class))
                    )
            }
    )
    @DeleteMapping(value = "/{linkId}")
    public ResponseResult<Object> deleteLink(@PathVariable("linkId") Long linkId) {

        return linkService.deleteLink(linkId) ;
    }

    @Operation(
            summary = "Change link status",
            description = "Change the status of a link based on the provided new status",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO encapsulated new status information",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = LinkChangeStatusDto.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns a message indicating the successful status change",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request data"
                    ),
                    @ApiResponse(
                            responseCode = "624",
                            description = "Link Not Found",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SystemException.class))
                    )
            }
    )
    @PutMapping(value = "/changeLinkStatus")
    public ResponseResult<Object> changeLinkStatus(@Valid @RequestBody LinkChangeStatusDto linkChangeStatusDto) {

        return linkService.changeLinkStatus(linkChangeStatusDto) ;
    }

    @Operation(
            summary = "Export links",
            description = "Export link data to Excel upon clicking the export button in the admin link management interface",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Links exported successfully"
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
    @PreAuthorize("@ps.hasPermission('content:link:export')")
    @GetMapping(value = "/export")
    public void export(HttpServletResponse response) throws IOException {
        try {
            WebUtils.setDownLoadHeader("友情連結導出.xlsx", response);

            List<Link> links = linkService.listExistLinks();
            List<ExcelLinkVo> excelLinkVos = BeanCopyUtil.copyBeanList(links, ExcelLinkVo.class);

            EasyExcel.write(response.getOutputStream(), ExcelLinkVo.class).autoCloseStream(Boolean.FALSE).sheet("友情連結導出")
                    .doWrite(excelLinkVos);

        } catch (Exception e) {

            ResponseResult<Object> result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR) ;

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(result) ;

            WebUtils.renderString(response, jsonString);
        }
    }
}
