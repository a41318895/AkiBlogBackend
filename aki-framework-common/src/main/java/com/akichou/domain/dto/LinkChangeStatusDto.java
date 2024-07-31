package com.akichou.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for changing the status of a link.
 *
 * <p>
 * Contains the identifier of the link and its new status.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/17 Wed.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "LinkChangeStatusDto", description = "identifier and new status Dto")
public class LinkChangeStatusDto {

    @Schema(name = "id", description = "link id")
    @NotNull(message = "ID must not be NULL !")
    private Long id ;

    @Schema(name = "status", description = "link status ( 0 -> pass the review, 1 -> no pass the review, 2 -> hasn't been reviewed )")
    @NotBlank(message = "Status must not be BLANK !")
    private String status ;
}
