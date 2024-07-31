package com.akichou.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.akichou.constant.SystemConstants.*;

/**
 * Rate Limit Interceptor
 *
 * @author Aki Chou
 * @date 2024/07/26 Fri.
 */
@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private final ConcurrentMap<String, RequestInfo> requestCounts = new ConcurrentHashMap<>();
    private static final long LIMIT = 1000 * 60 * 2 ;  // 2 minute

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {

        String path = request.getRequestURI();

        if (path.startsWith(VERIFY_CODE_PATH_TO_LIMIT) || path.startsWith(PASSWORD_FORGOT_PATH_TO_LIMIT)) {

            String ip = request.getRemoteAddr();
            long now = System.currentTimeMillis();

            RequestInfo requestInfo = requestCounts.computeIfAbsent(ip, k -> new RequestInfo());
            requestInfo.update(now);

            if (requestInfo.getRequestCount(now) > MAX_REQUESTS) {
                response.setStatus(429);  // Too many requests
                return false;
            }
        }
        return true;
    }

    private static class RequestInfo {

        private final AtomicInteger count = new AtomicInteger(0);
        private long lastRequestTime = 0;

        public void update(long now) {
            if (now - lastRequestTime > LIMIT) {
                count.set(1);
                lastRequestTime = now;
            } else {
                count.incrementAndGet();
            }
        }

        public int getRequestCount(long now) {
            if (now - lastRequestTime > LIMIT) {
                return 0;
            }
            return count.get();
        }
    }
}