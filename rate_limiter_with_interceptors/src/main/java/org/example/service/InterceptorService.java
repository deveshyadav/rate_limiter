package org.example.service;


import org.example.exception.RateLimitException;
import org.example.rate_limiters.RateLimiter;
import org.example.rate_limiters.RateLimiterFactory;

public class InterceptorService {

    private static final InterceptorService instance = new InterceptorService();

    private static final String rateLimiterType = "sliding";



    // Rate limiters for Foo (already defined)
    private final RateLimiter fooLimiter = RateLimiterFactory.create(rateLimiterType,2, 6000);
    private final RateLimiter aMethodLimiter = RateLimiterFactory.create(rateLimiterType,1, 6000);
    private final RateLimiter bMethodLimiter = RateLimiterFactory.create(rateLimiterType,1, 6000);

    // New rate limiters for Bar
    private final RateLimiter barLimiter = RateLimiterFactory.create(rateLimiterType,2, 6000);
    private final RateLimiter xMethodLimiter = RateLimiterFactory.create(rateLimiterType,1, 6000);
    private final RateLimiter yMethodLimiter = RateLimiterFactory.create(rateLimiterType,1, 6000);

    private InterceptorService() {}

    public static InterceptorService getInterceptorService() {
        return instance;
    }

    // Existing method for Foo
    public void checkLimitFoo(String methodName) {
        if (!fooLimiter.allowRequest()) {
            throw new RateLimitException("Rate limit exceeded for Foo class");
        }
        if ("a".equals(methodName)) {
            System.out.println("<<<<<<<<<<<<<<<<<<<<<Rate limiter for Foo.a() method");
            if (!aMethodLimiter.allowRequest()) {
                throw new RateLimitException("Rate limit exceeded for method a in Foo");
            }
        } else if ("b".equals(methodName)) {
            if (!bMethodLimiter.allowRequest()) {
                throw new RateLimitException("Rate limit exceeded for method b in Foo");
            }
        }
    }

    // New method for Bar
    public void checkLimitBar(String methodName) {
        if (!barLimiter.allowRequest()) {
            throw new RateLimitException("Rate limit exceeded for Bar class");
        }
        if ("x".equals(methodName)) {
            if (!xMethodLimiter.allowRequest()) {
                throw new RateLimitException("Rate limit exceeded for method x in Bar");
            }
        } else if ("y".equals(methodName)) {
            if (!yMethodLimiter.allowRequest()) {
                throw new RateLimitException("Rate limit exceeded for method y in Bar");
            }
        }
    }
}
