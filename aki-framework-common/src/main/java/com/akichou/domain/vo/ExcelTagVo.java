package com.akichou.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the tag information for import an Excel file.
 *
 * @author Aki Chou
 * @date 2024/07/20 Sat.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "ExcelTagVo", description = "Import tag as excel VO")
public class ExcelTagVo {

    @Schema(name = "id", description = "tag id")
    @ExcelProperty("標籤識別ID")
    private Long id ;

    @Schema(name = "name", description = "tag name")
    @ExcelProperty("標籤名稱")
    private String name ;

    @Schema(name = "remark", description = "tag remarks")
    @ExcelProperty("標籤備註")
    private String remark ;
}

