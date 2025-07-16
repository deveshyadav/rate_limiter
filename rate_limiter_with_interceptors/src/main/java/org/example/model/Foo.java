package org.example.model;

import org.example.service.InterceptorService;

public class Foo {
    InterceptorService interceptorService;

    public Foo(InterceptorService interceptorService)
    {
        this.interceptorService = interceptorService;
    }

    public void a()
    {
       interceptorService.checkLimitFoo("a");
       System.out.println("Method a called");
    }
    public void b()
    {
        interceptorService.checkLimitFoo("b");
        System.out.println("Method b called");
    }
}
