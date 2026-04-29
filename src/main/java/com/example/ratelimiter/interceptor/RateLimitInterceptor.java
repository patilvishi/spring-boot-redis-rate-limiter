package com.example.ratelimiter.interceptor;

import com.example.ratelimiter.exception.RateLimitException;
import com.example.ratelimiter.limiter.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RateLimiterService rateLimiterService;

    public RateLimitInterceptor(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String clientIp = request.getRemoteAddr();

        boolean allowed = rateLimiterService.isAllowed(clientIp);

        if (!allowed) {
            throw new RateLimitException("Too many requests");
        }

        return true;
    }
}