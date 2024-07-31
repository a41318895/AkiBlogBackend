package com.akichou.controller;

import com.akichou.domain.ResponseResult;
import com.akichou.domain.vo.LinkVo;
import com.akichou.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Blog Front-Stage Link Controller
 *
 * <p>
 * This controller manages front-stage operations related to links.
 * It handles the retrieval of all links, which can be used for displaying external resources or partner links.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/24 Wed.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/link")
@Tag(name = "Link Controller API", description = "Front-Stage Controller about handling links")
public class LinkController {

    private final LinkService linkService ;

    @Operation(
            summary = "Get All Links",
            description = "Retrieve a list of all links available in the system",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved link list",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = List.class,
                                            subTypes = LinkVo.class
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
    @RequestMapping(value = "/getAllLink", method = RequestMethod.GET)
    public ResponseResult<List<LinkVo>> getAllLink() {

        return linkService.getAllLink() ;
    }
}
