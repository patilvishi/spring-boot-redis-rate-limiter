package com.example.ratelimiter.limiter;

import org.springframework.stereotype.Component;

@Component
public class RateLimiterFactory {

    private final FixedWindowRateLimiter fixed;
    private final SlidingWindowRateLimiter sliding;
    private final TokenBucketRateLimiter token;

    public RateLimiterFactory(FixedWindowRateLimiter fixed,
                              SlidingWindowRateLimiter sliding,
                              TokenBucketRateLimiter token) {
        this.fixed = fixed;
        this.sliding = sliding;
        this.token = token;
    }

    public RateLimiterStrategy get(String type) {
        if ("sliding".equalsIgnoreCase(type)) return sliding;
        if ("token".equalsIgnoreCase(type)) return token;
        return fixed;
    }
}