package com.akichou.exception;

import com.akichou.customEnum.AppHttpCodeEnum;
import com.akichou.domain.ResponseResult;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global Exception Handler
 *
 * @author Aki Chou
 * @date 2024/06/20 Thu.
 */
//@ControllerAdvice
//@ResponseBody
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseResult<Object> systemExceptionHandler(SystemException e){

        log.error("System exception occurred: {}", e.getMessage(), e);

        return ResponseResult.errorResult(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseResult<String> handleConstraintViolationException(ConstraintViolationException e) {

        StringBuilder errors = new StringBuilder();

        e.getConstraintViolations().forEach(violation ->
                errors.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append("\n"));

        log.error("Validation exception occurred: {}", errors, e);

        return ResponseResult.errorResult(AppHttpCodeEnum.BAD_REQUEST, errors.toString()) ;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        log.error("Method argument not valid: {}", errors, e);

        return ResponseResult.errorResult(AppHttpCodeEnum.BAD_REQUEST.getCode(), errors.toString()) ;
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult<Object> exceptionHandler(Exception e){

        log.error("An unexpected exception occurred: {}", e.getMessage(), e);

        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
    }
}