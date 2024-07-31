package com.akichou.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the link information for import an Excel file.
 *
 * @author Aki Chou
 * @date 2024/07/20 Sat.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "ExcelLinkVo", description = "Import link as excel VO")
public class ExcelLinkVo {

    @Schema(name = "id", description = "link id")
    @ExcelProperty("友情連結識別ID")
    private Long id ;

    @Schema(name = "name", description = "link name")
    @ExcelProperty("友情連結名稱")
    private String name ;

    @Schema(name = "description", description = "link description")
    @ExcelProperty("友情連結描述")
    private String description ;

    @Schema(name = "logo", description = "link logo url")
    @ExcelProperty("友情連結LOGO圖片連結")
    private String logo ;

    @Schema(name = "address", description = "link url")
    @ExcelProperty("友情連結網址")
    private String address ;

    @Schema(name = "status", description = "link status ( 0 -> passed review, 1 -> no passed review, 2 -> hasn't been reviewed )")
    @ExcelProperty("友情連結狀態")
    private String status ;
}
