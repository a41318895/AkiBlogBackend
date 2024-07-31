package com.akichou.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * User Page Select Vo
 *
 * <p>
 * View Object representing detailed page information of a user.
 * </p>
 *
 * <p>
 * This VO encapsulates the page detailed attributes of a user, including their ID, username,
 * nickname, status, email, phone number, sex, avatar, create time, and update time. It is designed
 * to be used in scenarios where comprehensive user information is needed for display
 * or processing.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/19 Fri.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "UserPageSelectVo", description = "user detail page info VO")
public class UserPageSelectVo {

    @Schema(name = "id", description = "user id")
    private Long id;

    @Schema(name = "userName", description = "user username")
    private String userName ;

    @Schema(name = "nickName", description = "user nick name")
    private String nickName;

    @Schema(name = "status", description = "user status ( 0 -> usable, 1 -> forbidden )")
    private String status ;

    @Schema(name = "email", description = "user email")
    private String email;

    @Schema(name = "phoneNumber", description = "user phone number")
    private String phoneNumber ;

    @Schema(name = "sex", description = "user sex")
    private String sex;

    @Schema(name = "avatar", description = "user avatar")
    private String avatar;

    @Schema(name = "createTime", description = "user created time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Schema(name = "updateTime", description = "user updated time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime ;
}
