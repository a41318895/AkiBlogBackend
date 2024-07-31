package com.akichou.service.impl;

import com.akichou.customEnum.AppHttpCodeEnum;
import com.akichou.domain.LoginUser;
import com.akichou.domain.ResponseResult;
import com.akichou.domain.dto.UserLoginRequest;
import com.akichou.domain.vo.BlogUserLoginVo;
import com.akichou.domain.vo.UserInfoVo;
import com.akichou.exception.SystemException;
import com.akichou.service.BlogLoginService;
import com.akichou.util.BeanCopyUtil;
import com.akichou.util.JwtUtil;
import com.akichou.util.RedisCache;
import com.akichou.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static com.akichou.constant.SystemConstants.BLOG_LOGIN_REDIS_KEY_PRE;

/**
 * Blog Login Service Implementation
 *
 * @author Aki Chou
 * @date 2024/07/24 Wed.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BlogLoginServiceImpl implements BlogLoginService {

    private final AuthenticationManager authenticationManager;
    private final RedisCache redisCache;

    @Override
    public ResponseResult<BlogUserLoginVo> login(UserLoginRequest userLoginRequest) {

        // Token Authenticating
        Authentication authentication =
                authenticate(userLoginRequest.getUserName(), userLoginRequest.getPassword()) ;

        // Check null or not
        if(authentication == null){
            throw new SystemException(AppHttpCodeEnum.LOGIN_ERROR);
        }
        log.info("Authenticate successfully !") ;

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // Create JWT using LoginUser's userId
        String jwt = createJwt(loginUser) ;

        // Redis Caching
        redisCache.setCacheObject(BLOG_LOGIN_REDIS_KEY_PRE + loginUser.getUser().getId().toString(), loginUser);

        // Vo Encapsulation
        UserInfoVo userInfoVo = BeanCopyUtil.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(jwt, userInfoVo);

        return ResponseResult.okResult(blogUserLoginVo);
    }

    private Authentication authenticate(String username, String password) {

        // Generate Authentication Token
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authenticationToken);
    }

    private String createJwt(LoginUser loginUser) {

        String userId = loginUser.getUser().getId().toString();

        return JwtUtil.createJWT(userId);
    }

    @Override
    public ResponseResult<Object> logout() {

        // Get current status
        Long userId = SecurityUtil.getUserId() ;

        // Redis Cache Removing
        redisCache.deleteObject(BLOG_LOGIN_REDIS_KEY_PRE + userId);

        return ResponseResult.okResult() ;
    }
}