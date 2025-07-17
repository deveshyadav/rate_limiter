package org.example.rate_limiters;

import java.util.concurrent.atomic.AtomicLong;

public class TokenBucketRateLimiter implements RateLimiter{
    private final long capacity;          // Max tokens in the bucket
    private final long refillRate;        // Tokens added per second
    private final AtomicLong availableTokens;
    private long lastRefillTimestamp;

    public TokenBucketRateLimiter(long capacity, long refillRatePerSecond) {
        this.capacity = capacity;
        this.refillRate = refillRatePerSecond;
        this.availableTokens = new AtomicLong(capacity);
        this.lastRefillTimestamp = System.currentTimeMillis();
    }

    public synchronized boolean allowRequest() {
        refill();

        if (availableTokens.get() > 0) {
            availableTokens.decrementAndGet();
            return true;
        }
        return false;
    }

    private void refill() {
        long now = System.currentTimeMillis();
        long timeGapSinceLastRefillInSeconds = (now - lastRefillTimestamp) / 1000L;
        long tokensToAdd = timeGapSinceLastRefillInSeconds * refillRate;
        System.out.println("now: "+now);
        System.out.println("timeGapSinceLastRefillInSeconds: "+timeGapSinceLastRefillInSeconds);
        System.out.println("tokensToAdd: "+tokensToAdd);
        System.out.println("availableTokens: "+availableTokens);
        if (tokensToAdd > 0) {
            long newTokens = Math.min(capacity, availableTokens.get() + tokensToAdd);
            System.out.println("newTokens: "+newTokens);
            availableTokens.set(newTokens);
            lastRefillTimestamp = now;
            System.out.println("availableTokens after adding: "+availableTokens);
        }
    }
}

