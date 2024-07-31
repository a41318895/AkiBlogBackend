package com.akichou.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List ;

/**
 * DTO for adding a new article.
 *
 * <p>
 * This class represents the data transfer object for creating a new article.
 * It includes fields for the article's title, content, summary, category, thumbnail,
 * top status, status, comment ability, and tags. Each field is validated for required
 * constraints such as non-blank, size limits, and non-null values.
 * <p>
 *
 * @author Aki Chou
 * @date 2024/06/29 Sat.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "AddArticleDto", description = "dto for writing a new article")
public class ArticleAddDto {

    @Schema(name = "title", description = "article title")
    @NotBlank(message = "Title must not be BLANK !")
    @Size(max = 256, message = "Title is too long !")
    private String title;

    @Schema(name = "content", description = "article content")
    @NotBlank(message = "Content must not be BLANK !")
    private String content;

    @Schema(name = "summary", description = "article summary")
    @NotBlank(message = "Summary must not be BLANK !")
    @Size(max = 1024, message = "Summary is too long !")
    private String summary;

    @Schema(name = "categoryId", description = "article category id")
    @NotNull(message = "Category id must not be NULL !")
    private Long categoryId;

    @Schema(name = "thumbnail", description = "article thumbnail")
    @NotBlank(message = "Thumbnail must not be BLANK !")
    @Size(max = 1024, message = "Thumbnail is too long !")
    private String thumbnail;

    @Schema(name = "isTop", description = "is article on top ( 0 -> no, 1 -> yes )")
    @NotBlank(message = "IsTop must not be BLANK !")
    private String isTop;

    @Schema(name = "status", description = "article status ( 0 -> published, 1 -> draft )")
    @NotBlank(message = "Status must not be BLANK !")
    private String status;

    @Schema(name = "isComment", description = "can article be commented ( 0 -> no, 1 -> yes )")
    @NotBlank(message = "IsComment must not be BLANK !")
    private String isComment;

    @Schema(name = "tags", description = "tags binding with article")
    @NotEmpty(message = "Tags must not be EMPTY !")
    private List<Long> tags;
}
