package com.example.ratelimiter.limiter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class FixedWindowRateLimiter implements RateLimiterStrategy {

    private final StringRedisTemplate redis;

    @Value("${rate-limit.capacity}")
    private int capacity;

    @Value("${rate-limit.window-seconds}")
    private int window;

    public FixedWindowRateLimiter(StringRedisTemplate redis) {
        this.redis = redis;
    }

    public boolean isAllowed(String key) {
        String k = "fw:" + key;

        Long count = redis.opsForValue().increment(k);

        if (count == 1) {
            redis.expire(k, Duration.ofSeconds(window));
        }

        return count <= capacity;
    }
}