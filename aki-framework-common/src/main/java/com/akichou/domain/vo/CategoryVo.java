package com.akichou.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CategoryVo represents a view object for category data.
 *
 * <p>
 * This class encapsulates information about a category including its unique identifier,
 * name, and description.Used to show all categories.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/06/19 Wed.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "CategoryVo", description = "list all categories when write article VO")
public class CategoryVo {

    @Schema(name = "id", description = "category id")
    private Long id ;

    @Schema(name = "name", description = "category name")
    private String name ;

    @Schema(name = "description", description = "category description")
    private String description ;
}
