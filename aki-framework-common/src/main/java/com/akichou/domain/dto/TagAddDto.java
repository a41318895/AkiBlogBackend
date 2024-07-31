package com.akichou.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Add Tag DTO
 *
 * <p>
 * Data Transfer Object for attributes required to add a new tag.
 * </p>
 *
 * <p>
 * This DTO encapsulates the attributes needed to create a new tag,
 * including the tag name and remarks.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/19 Fri.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "TagAddDto", description = "Attributes about adding a new tag")
public class TagAddDto {

    @Schema(name = "name", description = "tag name")
    @NotBlank(message = "Name must not be BLANK !")
    private String name ;

    @Schema(name = "remark", description = "tag remarks")
    @NotBlank(message = "Remark must not be BLANK !")
    private String remark ;
}
