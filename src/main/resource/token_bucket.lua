local key = KEYS[1]
local capacity = tonumber(ARGV[1])
local tokens = tonumber(ARGV[2])
local now = tonumber(ARGV[3])
local refill_rate = tonumber(ARGV[4])

local data = redis.call("HMGET", key, "tokens", "timestamp")
local current_tokens = tonumber(data[1]) or capacity
local last_refill = tonumber(data[2]) or now

local delta = math.max(0, now - last_refill)
local refill = delta * refill_rate

current_tokens = math.min(capacity, current_tokens + refill)

if current_tokens < 1 then
    return 0
end

current_tokens = current_tokens - 1

redis.call("HMSET", key, "tokens", current_tokens, "timestamp", now)

return 1