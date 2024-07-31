package com.akichou.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Tag Page Select Vo
 *
 * <p>
 * View Object for detailed page information about a tag.
 * </p>
 *
 * <p>
 * This VO encapsulates the page details of a tag, including the tag ID, name, remarks, and
 * create / update time.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/25 Thu.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "TagPageSelectVo", description = "Tag page select info VO")
public class TagPageSelectVo {

    @Schema(name = "id", description = "tag id")
    private Long id ;

    @Schema(name = "name", description = "tag name")
    private String name ;

    @Schema(name = "remark", description = "tag remarks")
    private String remark ;

    @Schema(name = "createTime", description = "tag create time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Schema(name = "updateTime", description = "tag updated time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
