package com.akichou.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Tag Select Conditions Dto
 *
 * <p>
 * Data Transfer Object for filtering tags based on specific conditions.
 * </p>
 *
 * <p>
 * This DTO encapsulates the conditions used for filtering tag information,
 * including the tag name and remarks.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/18 Thu.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "TagSelectConditionsDto", description = "Conditions to filter tag dto")
public class TagSelectConditionsDto {

    @Schema(name = "name", description = "tag name")
    private String name ;

    @Schema(name = "remark", description = "tag remarks")
    private String remark ;
}
