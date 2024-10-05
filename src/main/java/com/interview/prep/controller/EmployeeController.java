package com.interview.prep.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EmployeeController {

    @GetMapping("/employees")
    public Map<String, String> getEmployees() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "This is the employee data.");
        response.put("status", "success");
        return response;
    }
}

