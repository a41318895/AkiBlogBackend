package com.akichou.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CategoryEditDto represents a data transfer object (DTO) for editing an existing category.
 *
 * <p>
 * This DTO encapsulates data required to update an existing category, including id, name, status, description.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/17 Wed.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "CategoryEditDto", description = "Dto encapsulates required attributes for editing category")
public class CategoryEditDto {

    @Schema(name = "id", description = "category id")
    @NotNull(message = "ID must not be NULL !")
    private Long id ;

    @Schema(name = "name", description = "category name")
    @NotBlank(message = "Name must not be BLANK !")
    private String name ;

    @Schema(name = "status", description = "category status ( 0 -> usable, 1 -> forbidden )")
    @NotBlank(message = "Status must not be BLANK !")
    private String status ;

    @Schema(name = "description", description = "category description")
    @NotBlank(message = "Description must not be BLANK !")
    private String description ;
}
