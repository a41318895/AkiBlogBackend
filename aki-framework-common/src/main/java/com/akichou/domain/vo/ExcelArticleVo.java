package com.akichou.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Represents the article information for import an Excel file.
 *
 * @author Aki Chou
 * @date 2024/07/20 Sat.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "ExcelArticleVo", description = "Import article as excel VO")
public class ExcelArticleVo {

    @Schema(name = "id", description = "article id")
    @ExcelProperty("文章識別ID")
    private Long id ;

    @Schema(name = "title", description = "article title")
    @ExcelProperty("文章標題")
    private String title ;

    @Schema(name = "summary", description = "article summary")
    @ExcelProperty("文章大綱")
    private String summary ;

    @Schema(name = "createTime", description = "article created time")
    @ExcelProperty("文章創建日期")
    private Date createTime ;
}
