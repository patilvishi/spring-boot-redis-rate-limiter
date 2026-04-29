# Spring Boot + Redis Rate Limiter

## Features
- Fixed Window Rate Limiting
- Redis-based distributed limiter
- Per-IP limiting
- 429 response handling

## Config
rate-limit:
  capacity: 5
  window-seconds: 60

## Run
1. Start Redis
2. Run Spring Boot
3. Hit /api/test

## Tech
Spring Boot + Redis