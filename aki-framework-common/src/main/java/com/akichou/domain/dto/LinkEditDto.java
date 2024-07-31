package com.akichou.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for editing link information.
 *
 * <p>
 * Contains details about a link, including its id, name, status, description, address, and logo.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/17 Wed.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "LinkEditDto", description = "New version of link info Dto")
public class LinkEditDto {

    @Schema(name = "id", description = "link id")
    @NotNull(message = "IO must not be NULL !")
    private Long id ;

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
