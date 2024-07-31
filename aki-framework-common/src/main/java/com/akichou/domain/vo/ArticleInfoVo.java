package com.akichou.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * View object (VO) for representing detailed information about an article.
 *
 * <p>
 * This class encapsulates detailed information about an article, including its ID,
 * title, content, summary, category ID, status, thumbnail URL, associated tags,
 * top status, and comment availability.
 * <p>
 *
 * @author Aki Chou
 * @date 2024/07/17 Wed.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "ArticleInfoVo", description = "article detail info VO")
public class ArticleInfoVo {

    @Schema(name = "id", description = "article id")
    private Long id;

    @Schema(name = "title", description = "article title")
    private String title;

    @Schema(name = "content", description = "article content")
    private String content;

    @Schema(name = "summary", description = "article summary")
    private String summary;

    @Schema(name = "categoryId", description = "id of category article belongs to")
    private Long categoryId ;

    @Schema(name = "status", description = "article status ( 0 -> published, 1 -> draft )")
    private String status ;

    @Schema(name = "thumbnail", description = "article thumbnail")
    private String thumbnail;

    @Schema(name = "tags", description = "tags binding with article")
    private List<Long> tags ;

    @Schema(name = "isTop", description = "is article on top ( 0 -> no, 1 -> yes )")
    private String isTop;

    @Schema(name = "isComment", description = "can article be commented ( 0 -> no, 1 -> yes )")
    private String isComment;
}
