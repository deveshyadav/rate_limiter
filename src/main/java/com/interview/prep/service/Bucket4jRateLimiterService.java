package com.interview.prep.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class Bucket4jRateLimiterService {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public Bucket resolveBucket(String userId) {
        return buckets.computeIfAbsent(userId, this::newBucket);
    }

    private Bucket newBucket(String userId) {
        Bandwidth limit = Bandwidth.classic(5, Refill.greedy(5, Duration.ofMinutes(1)));
        return Bucket.builder().addLimit(limit).build();
    }

    public boolean isAllowed(String userId) {
        Bucket bucket = resolveBucket(userId);
        return bucket.tryConsume(1); // Try to consume a token for each request
    }
}

