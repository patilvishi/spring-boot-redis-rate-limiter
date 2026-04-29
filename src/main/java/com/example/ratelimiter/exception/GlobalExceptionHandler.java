package com.example.ratelimiter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RateLimitException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public Map<String, String> handleRateLimit(RateLimitException ex) {
        return Map.of(
                "error", "Too Many Requests",
                "message", ex.getMessage()
        );
    }
}