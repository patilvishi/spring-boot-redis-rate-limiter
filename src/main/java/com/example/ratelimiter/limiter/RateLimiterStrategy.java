package com.example.ratelimiter.limiter;

public interface RateLimiterStrategy {
    boolean isAllowed(String key);
}