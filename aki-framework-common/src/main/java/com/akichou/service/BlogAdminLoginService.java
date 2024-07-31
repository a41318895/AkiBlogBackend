package com.akichou.service;

import com.akichou.domain.ResponseResult;
import com.akichou.domain.dto.UserLoginRequest;
import com.akichou.domain.vo.AdminUserLoginVo;

/**
 * Blog Admin Login Service Interface
 *
 * @author Aki Chou
 * @date 2024/06/24 Mon.
 */
public interface BlogAdminLoginService {

    ResponseResult<AdminUserLoginVo> adminLogin(UserLoginRequest userLoginRequest);

    ResponseResult<Object> logout();
}
