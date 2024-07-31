package com.akichou.controller;

import com.akichou.domain.ResponseResult;
import com.akichou.exception.SystemException;
import com.akichou.service.OssUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Blog Front-Stage Upload Controller
 *
 * <p>
 * This controller handles operations related to uploading files.
 * It provides functionality for uploading images to the server.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/24 Wed.
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Upload Controller API", description = "Front-Stage Controller about handling uploading")
public class UploadController {

    private final OssUploadService ossUploadService ;

    @Operation(
            summary = "Upload Image",
            description = "Upload an image file to the server",
            requestBody = @RequestBody(
                    description = "Multipart file containing the image to be uploaded",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully uploaded the image",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseResult.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request parameters or file format"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseResult.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "607",
                            description = "File Type Error (Only jpg / png)",
                            content = @Content(
                                    schema = @Schema(implementation = SystemException.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "608",
                            description = "File Size Error (Only smaller than 2MB)",
                            content = @Content(
                                    schema = @Schema(implementation = SystemException.class)
                            )
                    )
            }
    )
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseResult<Object> uploadImg(@RequestParam("img") MultipartFile img) {

        return ossUploadService.uploadImg(img) ;
    }
}
