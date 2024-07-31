package com.akichou.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Check Info Dto
 *
 * <p>
 *      To Check the detail information of user who forgot password DTO
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/26 Fri.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "CheckInfoDto", description = "To check user information for forgot password")
public class CheckInfoDto {

    @Schema(name = "userName", description = "user user name")
    @NotBlank(message = "UserName must not be BLANK !")
    private String userName ;

    @Schema(name = "nickName", description = "user nick name")
    @NotBlank(message = "NickName must not be BLANK !")
    private String nickName ;

    @Schema(name = "email", description = "user email")
    @NotBlank(message = "Email must not be BLANK !")
    private String email ;

    @Schema(name = "phoneNumber", description = "user phoneNumber")
    @NotBlank(message = "PhoneNumber must not be BLANK !")
    private String phoneNumber ;
}
