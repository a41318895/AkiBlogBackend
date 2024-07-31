package com.akichou.exception;

import com.akichou.customEnum.AppHttpCodeEnum;
import lombok.Getter;

/**
 * System Exception Class
 *
 * @author Aki Chou
 * @date 2024/06/20 Thu.
 */
@Getter
public class SystemException extends RuntimeException{

    private final int code;
    private final String message;

    public SystemException(AppHttpCodeEnum httpCodeEnum) {

        super(httpCodeEnum.getMessage());

        this.code = httpCodeEnum.getCode();
        this.message = httpCodeEnum.getMessage();
    }
}