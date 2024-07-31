package com.akichou.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CategoryAddDto represents a data transfer object (DTO) for adding a new category.
 *
 * <p>
 * This DTO encapsulates data required to create a new category, including name, status, and description.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/17 Wed.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "CategoryAddDto", description = "dto encapsulates required attributes for adding new category")
public class CategoryAddDto {

    @Schema(name = "name", description = "category name")
    @NotBlank(message = "Name must not be BLANK !")
    private String name ;

    @Schema(name = "status", description = "category status (0 -> usable, 1 -> forbidden)")
    @NotBlank(message = "Status must not be BLANK !")
    private String status ;

    @Schema(name = "description", description = "category description")
    @NotBlank(message = "Description must not be BLANK !")
    private String description ;
}
