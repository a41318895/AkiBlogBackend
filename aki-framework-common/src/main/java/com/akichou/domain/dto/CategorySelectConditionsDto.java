package com.akichou.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CategorySelectConditionsDto represents a data transfer object (DTO) for filtering categories.
 *
 * <p>
 * This DTO encapsulates filtering conditions for categories, including name and status.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/17 Wed.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CategorySelectConditionsDto", description = "Conditions to filter showed categories")
public class CategorySelectConditionsDto {

    @Schema(name = "name", description = "category name (fuzzy search)")
    private String name ;

    @Schema(name = "status", description = "category status (0 -> usable, 1 -> forbidden)")
    private String status ;
}
