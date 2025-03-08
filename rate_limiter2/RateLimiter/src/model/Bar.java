package model;

import service.InterceptorService;

public class Bar {

    InterceptorService interceptorService;

    public Bar(InterceptorService interceptorService) {
        this.interceptorService = interceptorService;
    }

    public void x() {
        interceptorService.checkLimitBar("x");
        System.out.println("Method x called");
    }

    public void y() {
        interceptorService.checkLimitBar("y");
        System.out.println("Method y called");
    }
}
