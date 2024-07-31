package com.akichou.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Article Value Object (VO)
 *
 * <p>
 * Represents information about an article for listing, including its content, metadata,
 * and status. This VO is used to transfer article details in responses, typically
 * in contexts where full article information is needed for display or processing.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/19 Fri.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "ArticleVo", description = "article info VO")
public class ArticleVo {

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

    @Schema(name = "categoryName", description = "name of category article belongs to")
    private String categoryName;

    @Schema(name = "status", description = "article status ( 0 -> published, 1 -> draft )")
    private String status ;

    @Schema(name = "thumbnail", description = "article thumbnail")
    private String thumbnail;

    @Schema(name = "isTop", description = "Is article on top ( 0 -> no, 1 -> yes )")
    private String isTop ;

    @Schema(name = "viewCount", description = "article view count")
    private Long viewCount;

    @Schema(name = "isComment", description = "Can article be commented ( 0 -> no, 1 -. yes )")
    private String isComment ;

    @Schema(name = "createTime", description = "article created time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Schema(name = "createBy", description = "id article created by")
    private Long createBy ;

    @Schema(name = "updateTime", description = "article updated time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime ;

    @Schema(name = "updateBy", description = "id article created by")
    private Long updateBy ;

    @Schema(name = "delFlag", description = "article login delete sign ( 0 -> exist, 1 -> deleted )")
    private Integer delFlag;
}
