package org.example.rate_limiters;

public class RateLimiterFactory {
    public static RateLimiter create(String type, long capacity, long rateOrWindow) {
        return switch (type.toLowerCase()) {
            case "token" -> new TokenBucketRateLimiter(capacity, rateOrWindow);
            case "sliding" -> new SlidingWindowRateLimiter((int) capacity, rateOrWindow);
            default -> throw new IllegalArgumentException("Unknown limiter type: " + type);
        };
    }
}
