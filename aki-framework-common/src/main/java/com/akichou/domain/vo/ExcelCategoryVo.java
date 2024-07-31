package com.akichou.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the category information for import an Excel file.
 *
 * @author Aki Chou
 * @date 2024/07/20 Sat.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "ExcelCategoryVo", description = "Import category as excel VO")
public class ExcelCategoryVo {

    @Schema(name = "name", description = "category name")
    @ExcelProperty("分類名稱")
    private String name ;

    @Schema(name = "deacription", description = "category description")
    @ExcelProperty("分類描述")
    private String description ;

    @Schema(name = "status", description = "category status ( 0 -> usable, 1 -> forbidden )")
    @ExcelProperty("分類狀態")
    private String status ;

}
