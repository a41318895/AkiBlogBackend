package com.akichou.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Add Link DTO
 *
 * <p>
 * Data Transfer Object for adding a new link.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/17 Wed.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "LinkAddDto", description = "new link info Dto")
public class LinkAddDto {

    @Schema(name = "name", description = "link name")
    @NotBlank(message = "Name must not be BLANK !")
    private String name ;

    @Schema(name = "status", description = "link status ( 0 -> pass the review, 1 -> no pass the review, 2 -> hasn't been reviewed )")
    @NotBlank(message = "Status must not be BLANK !")
    private String status ;

    @Schema(name = "description", description = "link description")
    @NotBlank(message = "Description must not be BLANK !")
    private String description ;

    @Schema(name = "address", description = "link url")
    @NotBlank(message = "Address must not be BLANK !")
    private String address ;

    @Schema(name = "logo", description = "link logo url")
    @NotBlank(message = "Logo must not be BLANK !")
    private String logo ;
}

