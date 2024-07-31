package com.akichou.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Add Comment Data Transfer Object (DTO)
 *
 * <p>
 * This class is used to encapsulate the data required for adding a new comment.
 * It includes information about the comment, such as its content, the article it belongs to,
 * and metadata like creation and update timestamps.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/20 Sat.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "CommentAddDto", description = "Attributes for adding new comment")
public class CommentAddDto {

    @Schema(name = "type", description = "comment type")
    @NotBlank(message = "Type must not be BLANK !")
    private String type;

    @Schema(name = "articleId", description = "id of article commented")
    @NotNull(message = "ArticleId must not be NULL !")
    private Long articleId;

    @Schema(name = "rootId", description = "comment root id ( -1 -> root comment itself )")
    @NotNull(message = "RootId must not be NULL !")
    private Long rootId;

    @Schema(name = "content", description = "comment content")
    @NotBlank(message = "Content must not be BLANK !")
    private String content;

    @Schema(name = "toCommentUserId", description = "id of user commented to")
    @NotNull(message = "ToCommentUserId must not be NULL !")
    private Long toCommentUserId;

    @Schema(name = "toCommentId", description = "id of comment commented to")
    @NotNull(message = "ToCommentId must not be NULL !")
    private Long toCommentId;
}
