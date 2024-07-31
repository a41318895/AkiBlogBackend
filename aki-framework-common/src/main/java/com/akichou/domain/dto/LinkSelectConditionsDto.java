package com.akichou.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Link List Conditions DTO
 *
 * <p>
 * Data Transfer Object for filtering link conditions.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/17 Wed.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "LinkSelectConditionsDto", description = "Conditions to filter link dto")
public class LinkSelectConditionsDto {

    @Schema(name = "name", description = "link name (fuzzy search)")
    private String name ;

    @Schema(name = "status", description = "link status ( 0 -> pass the review, 1 -> no pass the review, 2 -> hasn't been reviewed )")
    private String status ;
}
