package com.akichou.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Edit Tag DTO
 *
 * <p>
 * Data Transfer Object for attributes required to edit an existing tag.
 * </p>
 *
 * <p>
 * This DTO encapsulates the attributes needed to update an existing tag,
 * including the tag ID, name, and remarks.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/19 Fri.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "TagEditDto", description = "new version tag info dto")
public class TagEditDto {

    @Schema(name = "id", description = "tag id")
    @NotNull(message = "ID must not be NULL !")
    private Long id ;

    @Schema(name = "name", description = "tag name")
    @NotBlank(message = "Name must not be BLANK !")
    private String name ;

    @Schema(name = "remark", description = "tag remarks")
    @NotBlank(message = "Remark must not be BLANK !")
    private String remark ;
}
