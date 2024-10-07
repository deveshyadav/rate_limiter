/*package com.interview.prep.interceptor;

import com.interview.prep.service.Bucket4jRateLimiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class Bucket4jRateLimiterInterceptor implements HandlerInterceptor {

    @Autowired
    private Bucket4jRateLimiterService rateLimiterService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getRemoteAddr(); // You can customize this logic

        if (!rateLimiterService.isAllowed(userId)) {
            response.setStatus(429);
            response.getWriter().write("Too many requests. Please try again later.");
            return false;
        }

        return true;
    }
}*/

