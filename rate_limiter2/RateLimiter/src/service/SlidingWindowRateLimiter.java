package service;

import java.util.LinkedList;
import java.util.Queue;

public class SlidingWindowRateLimiter {
    private final int maxRequests;
    private final long timeWindowMillis;
    private final Queue<Long> timestamps = new LinkedList<>();

    public SlidingWindowRateLimiter(int maxRequests, long timeWindowMillis) {
        this.maxRequests = maxRequests;
        this.timeWindowMillis = timeWindowMillis;
    }

    public synchronized boolean allowRequest() {
        long now = System.currentTimeMillis();
        // Remove timestamps outside the current window
        while (!timestamps.isEmpty() && now - timestamps.peek() > timeWindowMillis) {
            timestamps.poll();
        }
        if (timestamps.size() < maxRequests) {
            timestamps.add(now);
            return true;
        }
        return false;
    }
}
