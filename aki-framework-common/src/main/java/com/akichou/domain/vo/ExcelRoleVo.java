package com.akichou.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Represents the role information for import an Excel file.
 *
 * @author Aki Chou
 * @date 2024/07/20 Sat.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "ExcelRoleVo", description = "Import role as excel VO")
public class ExcelRoleVo {

    @Schema(name = "id", description = "role id")
    @ExcelProperty("角色識別ID")
    private Long id ;

    @Schema(name = "roleName", description = "role name")
    @ExcelProperty("角色名稱")
    private String roleName ;

    @Schema(name = "roleKey", description = "role key identifier")
    @ExcelProperty("角色權限識別")
    private String roleKey ;

    @Schema(name = "roleSort", description = "role sort sign")
    @ExcelProperty("角色排序依據")
    private String roleSort ;

    @Schema(name = "status", description = "role status ( 0 -> usable, 1 -> forbidden )")
    @ExcelProperty("角色狀態")
    private String status ;

    @Schema(name = "createTime", description = "role created time")
    @ExcelProperty("角色創建 / 修改時間")
    private Date createTime ;
}
