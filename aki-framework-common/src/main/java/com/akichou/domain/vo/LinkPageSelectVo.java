package com.akichou.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Link Page Select VO
 *
 * <p>
 * View Object for filtered link information.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/17 Wed.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "LinkPageSelectVo", description = "Filtered links' info VO")
public class LinkPageSelectVo {

    @Schema(name = "id", description = "link id")
    private Long id ;

    @Schema(name = "name", description = "link name")
    private String name ;

    @Schema(name = "status", description = "link status ( 0 -> pass the review, 1 -> no pass the review, 2 -> hasn't been reviewed )")
    private String status;

    @Schema(name = "description", description = "link description")
    private String description ;

    @Schema(name = "address", description = "link url")
    private String address ;

    @Schema(name = "logo", description = "link logo url")
    private String logo ;

    @Schema(name = "createTime", description = "link create time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Schema(name = "updateTime", description = "link updated time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
