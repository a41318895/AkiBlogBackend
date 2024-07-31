package com.akichou.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Link Info VO
 *
 * <p>
 * View Object for detailed information of a target link.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/17 Wed.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "LinkInfoVo", description = "target link detail info VO")
public class LinkInfoVo {

    @Schema(name = "id", description = "link id")
    private Long id ;

    @Schema(name = "name", description = "link name")
    private String name ;

    @Schema(name = "status", description = "link status ( 0 -> pass the review, 1 -> no pass the review, 2 -> hasn't been reviewed )")
    private String status ;

    @Schema(name = "description", description = "link description")
    private String description ;

    @Schema(name = "address", description = "link url")
    private String address ;

    @Schema(name = "logo", description = "link logo url")
    private String logo ;
}
