package com.akichou.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Link View Object (VO)
 *
 * <p>
 * This class is used to encapsulate the detailed information about a link.
 * It includes attributes such as the link's ID, name, description, logo URL, and address.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/20 Sat.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "LinkVo", description = "link detail info VO")
public class LinkVo {

    @Schema(name = "id", description = "link id")
    private Long id ;

    @Schema(name = "name", description = "link name")
    private String name ;

    @Schema(name = "description", description = "link description")
    private String description ;

    @Schema(name = "logo", description = "link logo url")
    private String logo ;

    @Schema(name = "address", description = "link url")
    private String address ;
}
