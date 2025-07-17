package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RateLimitException.class)
    public ResponseEntity<String> handleRateLimitExceeded(RateLimitException ex) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(ex.getMessage());
    }
}

