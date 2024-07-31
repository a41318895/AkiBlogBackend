package com.akichou.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * User Information View Object (VO)
 *
 * <p>
 * This class represents the view object for user details. It is used to encapsulate and
 * provide user information in a structured format. It typically serves as the response model
 * when retrieving user information from the system.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/20 Sat.
 */
@Data
@Accessors(chain = true)
@Tag(name = "UserInfoVo", description = "user detail info VO")
public class UserInfoVo {

    @Schema(name = "id", description = "user id")
    private Long id;

    @Schema(name = "nickName", description = "user nick name")
    private String nickName ;

    @Schema(name = "avatar", description = "user avatar url")
    private String avatar;

    @Schema(name = "sex", description = "user sex")
    private String sex;

    @Schema(name = "email", description = "user email")
    private String email ;
}