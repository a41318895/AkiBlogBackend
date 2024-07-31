package com.akichou.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for filtering conditions in article list.
 *
 * <p>
 * This class represents the data transfer object used to specify filtering conditions
 * when retrieving a list of articles. It includes fields for filtering articles by title
 * and summary using fuzzy search criteria.
 * <p>
 *
 * @author Aki Chou
 * @date 2024/07/17 Wed.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ArticleSelectConditionsDto", description = "conditions to filter article dto")
public class ArticleSelectConditionsDto {

    @Schema(name = "title", description = "article title (fuzzy search)")
    private String title ;

    @Schema(name = "summary", description = "article summary (fuzzy search)")
    private String summary ;
}
