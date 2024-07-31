package com.akichou.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for editing article
 *
 * <p>
 * This class encapsulates data for modifying article information. It includes fields such as
 * article ID, title, content, summary, category ID, status, thumbnail URL, top status, comment
 * availability, and associated tags.
 * <p>
 *
 * @author Aki Chou
 * @date 2024/07/17 Wed.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ArticleEditDto", description = "DTO encapsulated modifying article info")
public class ArticleEditDto {

    @Schema(name = "id", description = "article id")
    @NotNull(message = "ID must not be NULL !")
    private Long id;

    @Schema(name = "title", description = "article title")
    @NotBlank(message = "Title must not be BLANK !")
    @Size(max = 256, message = "Title is too long !")
    private String title;

    @Schema(name = "content", description = "article content")
    @NotBlank(message = "Content must not be BLANK !")
    private String content;

    @Schema(name = "summary", description = "文章大綱")
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
