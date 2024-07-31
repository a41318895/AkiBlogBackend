package com.akichou.controller;

import com.akichou.domain.ResponseResult;
import com.akichou.service.OssUploadService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Blog Admin Upload Management Controller
 *
 * <p>
 * This controller manages admin operations related to uploading images.
 * It handles functionalities such as uploading images to MinIO.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/24 Wed.
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Admin Upload Controller API", description = "Controller about uploading image to minio")
public class UploadController {

    private final OssUploadService ossUploadService ;

    @Operation(
            summary = "Upload an image",
            description = "Upload an image to MinIO",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "MultipartFile representing the image to be uploaded",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = MultipartFile.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Image successfully uploaded",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseResult.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid file input"
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
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseResult<Object> uploadImg(MultipartFile img) {

        return ossUploadService.uploadImg(img) ;
    }
}
