package com.example.ratelimiter.limiter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class SlidingWindowRateLimiter implements RateLimiterStrategy {

    private final StringRedisTemplate redis;

    @Value("${rate-limit.capacity}")
    private int capacity;

    @Value("${rate-limit.window-seconds}")
    private int window;

    public SlidingWindowRateLimiter(StringRedisTemplate redis) {
        this.redis = redis;
    }

    public boolean isAllowed(String key) {

        String k = "sw:" + key;
        long now = System.currentTimeMillis();

        redis.opsForZSet().removeRangeByScore(k, 0, now - window * 1000);

        Long count = redis.opsForZSet().zCard(k);

        if (count != null && count >= capacity) {
            return false;
        }

        redis.opsForZSet().add(k, String.valueOf(now), now);

        return true;
    }
}