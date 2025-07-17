package org.example;

import org.example.service.InterceptorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RateLimiterController {

    private final InterceptorService interceptorService = InterceptorService.getInterceptorService();

    @GetMapping("/foo/{method}")
    public ResponseEntity<String> callFoo(@PathVariable String method) {
        try {
            return ResponseEntity.ok("Foo method " + method + " executed successfully");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(ex.getMessage());
        }
    }

    @GetMapping("/bar/{method}")
    public ResponseEntity<String> callBar(@PathVariable String method) {
        try {
            return ResponseEntity.ok("Bar method " + method + " executed successfully");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(ex.getMessage());
        }
    }
}

