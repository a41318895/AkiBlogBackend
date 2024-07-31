package com.akichou.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Represents a comment display VO
 *
 * <p>
 * This class provides the details of a comment, including information about the article
 * it belongs to, the user who made the comment, and any potential replies to the comment.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/20 Sat.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "CommentVo", description = "Comment display VO")
public class CommentVo {

    @Schema(name = "id", description = "comment id")
    private Long id ;

    @Schema(name = "articleId", description = "id of article commented")
    private Long articleId;

    @Schema(name = "rootId", description = "comment root id ( -1 -> root comment itself )")
    private Long rootId;

    @Schema(name = "content", description = "comment content")
    private String content;

    @Schema(name = "toCommentUserId", description = "id of user commented to")
    private Long toCommentUserId;

    @Schema(name = "toCommentId", description = "id of comment commented to")
    private Long toCommentId;

    @Schema(name = "toCommentUserName", description = "user name of comment commented to")
    private String toCommentUserName;

    @Schema(name = "createTime", description = "comment created time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Schema(name = "createBy", description = "user id of comment created by")
    private Long createBy;

    @Schema(name = "username", description = "username of comment user")
    private String username;

    @Schema(name = "children", description = "child comments of itself")
    private List<CommentVo> children ;
}
