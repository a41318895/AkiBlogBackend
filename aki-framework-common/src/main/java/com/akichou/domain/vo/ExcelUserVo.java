package com.akichou.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Represents the user information for import an Excel file.
 *
 * @author Aki Chou
 * @date 2024/07/20 Sat.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "ExcelUserVo", description = "Import user as excel VO")
public class ExcelUserVo {

    @Schema(name = "id", description = "user id")
    @ExcelProperty("用戶識別ID")
    private Long id ;

    @Schema(name = "userName", description = "user user name")
    @ExcelProperty("用戶名稱")
    private String userName ;

    @Schema(name = "nickName", description = "user nick name")
    @ExcelProperty("用戶顯示暱稱")
    private String nickName ;

    @Schema(name = "phoneNumber", description = "user phone number")
    @ExcelProperty("用戶手機號碼")
    private String phoneNumber ;

    @Schema(name = "status", description = "user status ( 0 -> usable, 1 -> forbidden )")
    @ExcelProperty("用戶狀態")
    private String status ;

    @Schema(name = "createTime", description = "user created time")
    @ExcelProperty("用戶創建 / 修改時間")
    private Date createTime ;
}
