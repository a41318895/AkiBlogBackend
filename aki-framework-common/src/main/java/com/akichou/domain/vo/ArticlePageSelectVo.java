package com.akichou.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * View object (VO) for displaying article information.
 *
 * <p>
 * This class represents a view object used to display article details.
 * It includes fields such as article ID, title, content,
 * summary, category ID, status, thumbnail URL, top status, view count,
 * comment availability, create time, update time and deletion flag.
 * <p>
 *
 * @author Aki Chou
 * @date 2024/07/17 Wed.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "ArticlePageSelectVo", description = "article info for listing VO")
public class ArticlePageSelectVo {

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

    @Schema(name = "isTop", description = "is article on top ( 0 -> no, 1 -> yes )")
    private String isTop;

    @Schema(name = "viewCount", description = "article view count")
    private Long viewCount;

    @Schema(name = "isComment", description = "can article be commented ( 0 -> no, 1 -> yes )")
    private String isComment;

    @Schema(name = "createTime", description = "article create time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Schema(name = "updateTime", description = "article updated time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @Schema(name = "delFlag", description = "article login delete sign ( 0 -> exist, 1 -> deleted )")
    private Integer delFlag;
}
