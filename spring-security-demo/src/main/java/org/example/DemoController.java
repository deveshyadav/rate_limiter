package org.example;

import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DemoController {
    @GetMapping("/public/ping")
    public Map<String,String> publicPing() { return Map.of("status","ok"); }

    @GetMapping("/user/me")
    public Map<String,Object> me(@AuthenticationPrincipal Jwt jwt) { return Map.of("sub", jwt.getSubject(), "claims", jwt.getClaims()); }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/metrics")
    public Map<String,String> admin() { return Map.of("area","admin"); }
}
