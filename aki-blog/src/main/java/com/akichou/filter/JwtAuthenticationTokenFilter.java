package com.akichou.filter;

import com.akichou.customEnum.AppHttpCodeEnum;
import com.akichou.domain.LoginUser;
import com.akichou.domain.ResponseResult;
import com.akichou.util.JwtUtil;
import com.akichou.util.RedisCache;
import com.akichou.util.WebUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Objects;

import static com.akichou.constant.SystemConstants.BLOG_LOGIN_REDIS_KEY_PRE;

/**
 * JWT Authentication Token Filter
 *
 * @author Aki Chou
 * @date 2024/06/28 Fri.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final RedisCache redisCache;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws IOException, ServletException {
        // Get token from header
        String token = request.getHeader("token");

        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        Claims claims ;
        try {
            // Parse token
            claims = JwtUtil.parseJWT(token);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {

            log.error("Token error: {}", e.getMessage());
            ResponseResult<Object> result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, objectMapper.writeValueAsString(result));
            return;
        } catch (Exception e) {

            log.error("Unexpected error: {}", e.getMessage());
            ResponseResult<Object> result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, objectMapper.writeValueAsString(result));
            return;
        }

        String userid = claims.getSubject();

        LoginUser loginUser = redisCache.getCacheObject(BLOG_LOGIN_REDIS_KEY_PRE + userid, LoginUser.class);
        if (Objects.isNull(loginUser)) {

            ResponseResult<LoginUser> result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, objectMapper.writeValueAsString(result));
            return;
        }

        // Save user information into SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
