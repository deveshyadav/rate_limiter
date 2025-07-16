package org.example.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private final InterceptorService interceptorService = InterceptorService.getInterceptorService();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI(); // e.g., /api/foo/a
        String[] parts = path.split("/");

        if (path.contains("/foo/")) {
            interceptorService.checkLimitFoo(parts[parts.length - 1]);
        } else if (path.contains("/bar/")) {
            interceptorService.checkLimitBar(parts[parts.length - 1]);
        }
        return true; // continue to controller if allowed
    }
}

