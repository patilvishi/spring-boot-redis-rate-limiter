package com.example.ratelimiter.limiter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TokenBucketRateLimiter implements RateLimiterStrategy {

    private final StringRedisTemplate redis;
    private final DefaultRedisScript<Long> script;

    @Value("${rate-limit.capacity}")
    private int capacity;

    @Value("${rate-limit.refill-rate}")
    private double refillRate;

    public TokenBucketRateLimiter(StringRedisTemplate redis) {
        this.redis = redis;

        script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("token_bucket.lua"));
        script.setResultType(Long.class);
    }

    public boolean isAllowed(String key) {

        Long result = redis.execute(
                script,
                List.of("tb:" + key),
                String.valueOf(capacity),
                "1",
                String.valueOf(System.currentTimeMillis()),
                String.valueOf(refillRate)
        );

        return result != null && result == 1;
    }
}