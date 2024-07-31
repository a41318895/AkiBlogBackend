package com.akichou.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Hot Article Value Object (VO)
 *
 * <p>
 * Represents the information of a hot article, typically with a high view count.
 * </p>
 *
 * <p>
 * This VO is used to transfer data related to articles that are popular or trending
 * based on their view counts. It provides a snapshot of key details required for
 * displaying such articles.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/19 Fri.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "HotArticleVo", description = "hot article (high viewCount) display VO")
public class HotArticleVo {

    @Schema(name = "id", description = "article id")
    private Long id;

    @Schema(name = "title", description = "article title")
    private String title;

    @Schema(name = "viewCount", description = "article view count")
    private Long viewCount;
}
