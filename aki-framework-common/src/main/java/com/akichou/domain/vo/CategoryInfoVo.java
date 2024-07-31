package com.akichou.domain.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CategoryInfoVo represents a view object for retrieving category information.
 *
 * <p>
 * This class encapsulates information about a category including its unique identifier,
 * name, status, and description. It is used for fetching detailed category data.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/17 Wed.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "CategoryInfoVo", description = "target category info VO")
public class CategoryInfoVo {

    @Schema(name = "id", description = "category id")
    private Long id ;

    @Schema(name = "name", description = "category name")
    private String name ;

    @Schema(name = "status", description = "category status (0 -> usable, 1 -> forbidden)")
    private String status ;

    @Schema(name = "description", description = "category description")
    private String description ;
}
