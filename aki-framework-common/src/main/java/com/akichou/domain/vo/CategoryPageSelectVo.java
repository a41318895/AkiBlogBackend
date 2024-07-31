package com.akichou.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * CategoryPageSelectVo represents a view object for displaying category data in a selecting page.
 *
 * <p>
 * This class encapsulates information about a category suitable for rendering in the filtered list,
 * including its unique identifier, name, status, description, parent category identifier, and
 * create / update time.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/17 Wed.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "CategoryPageSelectVo", description = "Filtered category list info VO")
public class CategoryPageSelectVo {

    @Schema(name = "id", description = "category id")
    private Long id ;

    @Schema(name = "name", description = "category name")
    private String name ;

    @Schema(name = "status", description = "category status (0 -> usable, 1 -> forbidden)")
    private String status ;

    @Schema(name = "description", description = "category description")
    private String description ;

    @Schema(name = "pid", description = "super category id")
    private Long pid ;

    @Schema(name = "createTime", description = "category create time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Schema(name = "updateTime", description = "category updated time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
