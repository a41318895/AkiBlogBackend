package com.akichou.converter;

import com.akichou.domain.dto.UserAddDto;
import com.akichou.domain.dto.UserRegisterRequest;
import com.akichou.domain.entity.User;
import com.akichou.util.SecurityUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

import static com.akichou.constant.SystemConstants.*;

/**
 * User Converter
 *
 * @author Aki Chou
 * @date 2024/07/20 Sat.
 */
public class UserConverter {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static User mapRegisterUserRequestToUser(UserRegisterRequest userRegisterRequest) {

        return User.builder()
                .userName(userRegisterRequest.getUserName())
                .password(passwordEncoder.encode(userRegisterRequest.getPassword()))
                .email(userRegisterRequest.getEmail())
                .nickName(userRegisterRequest.getNickName())
                .phoneNumber(userRegisterRequest.getPhoneNumber())
                .type(USER_TYPE_NORMAL)
                .status(USER_STATUS_NORMAL)
                .delFlag(DEL_FLAG_EXIST)
                .createTime(new Date())
                .updateTime(new Date())
                .build();
    }

    public static User mapAddUserDtoToUser(UserAddDto userAddDto) {

        return User.builder()
                .userName(userAddDto.getUserName())
                .nickName(userAddDto.getNickName())
                .password(passwordEncoder.encode(userAddDto.getPassword()))
                .email(userAddDto.getEmail())
                .status(userAddDto.getStatus())
                .createBy(SecurityUtil.getUserId())
                .createTime(new Date())
                .updateBy(SecurityUtil.getUserId())
                .updateTime(new Date())
                .delFlag(0)
                .type("0")
                .sex(userAddDto.getSex())
                .phoneNumber(userAddDto.getPhoneNumber())
                .build();
    }
}
