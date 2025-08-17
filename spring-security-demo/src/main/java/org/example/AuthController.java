package org.example;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

record LoginRequest(String username, String password) {}

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired AuthenticationManager authManager;
    @Autowired TokenService tokens;

    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody LoginRequest req) {
        Authentication a = authManager.authenticate(new UsernamePasswordAuthenticationToken(req.username(), req.password()));
        String jwt = tokens.generate(a);
        return Map.of("access_token", jwt, "token_type", "Bearer");
    }
}

