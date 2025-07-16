package org.example.model;

import org.example.service.InterceptorService;

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
    //6530 2700 0461 1005
}
