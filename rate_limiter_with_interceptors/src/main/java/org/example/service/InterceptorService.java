package org.example.service;


public class InterceptorService {

    private static final InterceptorService instance = new InterceptorService();

    // Rate limiters for Foo (already defined)
    private final SlidingWindowRateLimiter fooLimiter = new SlidingWindowRateLimiter(5, 60000);
    private final SlidingWindowRateLimiter aMethodLimiter = new SlidingWindowRateLimiter(2, 60000);
    private final SlidingWindowRateLimiter bMethodLimiter = new SlidingWindowRateLimiter(3, 60000);

    // New rate limiters for Bar
    private final SlidingWindowRateLimiter barLimiter = new SlidingWindowRateLimiter(5, 60000);
    private final SlidingWindowRateLimiter xMethodLimiter = new SlidingWindowRateLimiter(2, 60000);
    private final SlidingWindowRateLimiter yMethodLimiter = new SlidingWindowRateLimiter(3, 60000);

    private InterceptorService() {}

    public static InterceptorService getInterceptorService() {
        return instance;
    }

    // Existing method for Foo
    public void checkLimitFoo(String methodName) {
        if (fooLimiter.allowRequest()) {
            throw new RateLimitException("Rate limit exceeded for Foo class");
        }
        if ("a".equals(methodName)) {
            if (aMethodLimiter.allowRequest()) {
                throw new RateLimitException("Rate limit exceeded for method a in Foo");
            }
        } else if ("b".equals(methodName)) {
            if (bMethodLimiter.allowRequest()) {
                throw new RateLimitException("Rate limit exceeded for method b in Foo");
            }
        }
    }

    // New method for Bar
    public void checkLimitBar(String methodName) {
        if (barLimiter.allowRequest()) {
            throw new RateLimitException("Rate limit exceeded for Bar class");
        }
        if ("x".equals(methodName)) {
            if (xMethodLimiter.allowRequest()) {
                throw new RateLimitException("Rate limit exceeded for method x in Bar");
            }
        } else if ("y".equals(methodName)) {
            if (yMethodLimiter.allowRequest()) {
                throw new RateLimitException("Rate limit exceeded for method y in Bar");
            }
        }
    }
}
