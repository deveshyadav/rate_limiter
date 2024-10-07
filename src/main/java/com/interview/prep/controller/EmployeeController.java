package com.interview.prep.controller;

import com.interview.prep.service.Bucket4jRateLimiterService;

import io.github.bucket4j.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
//import com.github.vladimir.bukhtoyarov.bucket4j.BucketInfo;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EmployeeController {

    @Autowired
    private Bucket4jRateLimiterService rateLimiterService;

    @GetMapping("/employees")
    public ResponseEntity<?> protectedResource(@RequestHeader("User-Id") String userId) {
        if (rateLimiterService.isAllowed(userId)) {
            // Process the request and return a response
            return ResponseEntity.ok("Request allowed");
        } else {
            // Handle rate limit exceeded
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Rate limit exceeded");
        }
    }

    @GetMapping("/get-bucket-info")
    public String getBucketInfo(@RequestHeader("User-Id") String userId) {
        Bucket bucket = rateLimiterService.resolveBucket(userId);
        if (bucket.tryConsume(1)) {
            return "Request accepted for user: " + userId;
        } else {
            return "Rate limit exceeded. Try again later.";
        }
    }
}

