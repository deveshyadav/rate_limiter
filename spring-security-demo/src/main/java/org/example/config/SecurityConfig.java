package org.example.config;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    /** Defines HTTP security rules and enables JWT-based auth for this API (stateless; CSRF disabled). */
    @Bean
    SecurityFilterChain api(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(a -> a
                        .requestMatchers("/auth/**", "/public/**").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .oauth2ResourceServer(o -> o.jwt(j -> j.jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    /** Maps JWT claims to Spring authorities: `roles` -> ROLE_*, `scope` (space-delimited) -> SCOPE_*. */
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter conv = new JwtAuthenticationConverter();
        conv.setJwtGrantedAuthoritiesConverter(this::extractAuthorities);
        return conv;
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        List<GrantedAuthority> out = new ArrayList<>();
        Object scope = jwt.getClaims().get("scope");
        if (scope instanceof String s) for (String v : s.split(" ")) out.add(new SimpleGrantedAuthority("SCOPE_" + v));
        Object roles = jwt.getClaims().get("roles");
        if (roles instanceof Collection<?> r) for (Object v : r) out.add(new SimpleGrantedAuthority("ROLE_" + v.toString()));
        return out;
    }


    /** In-memory user store for demo/login: user/pass (USER), admin/pass (ADMIN). Replace with real store later. */
    @Bean
    UserDetailsService users(PasswordEncoder pe) {
        UserDetails u1 = User.withUsername("user").password(pe.encode("pass")).roles("USER").build();
        UserDetails u2 = User.withUsername("admin").password(pe.encode("pass")).roles("ADMIN").build();
        return new InMemoryUserDetailsManager(u1, u2);
    }

    /** Password hashing for username/password authentication (BCrypt). */
    @Bean
    PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    /** Exposes the AuthenticationManager used by /auth/login to verify username/password. */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    /** Shared HMAC key for signing/verifying self-issued JWTs (HS256). Keep secret; length ≥ 32 bytes. */
    @Bean
    SecretKey jwtSecret(@Value("${app.jwt.secret}") String secret) {
        byte[] key = secret.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(key, "HmacSHA256");
    }

    /** Encodes (signs) JWTs with HS256 using the secret key. */
    @Bean
    JwtEncoder jwtEncoder(SecretKey key) {
        return new NimbusJwtEncoder(new ImmutableSecret<>(key));
    }

    /** Decodes (verifies) incoming JWTs with HS256 using the same secret key. */
    @Bean
    JwtDecoder jwtDecoder(SecretKey key) {
        return NimbusJwtDecoder.withSecretKey(key).macAlgorithm(MacAlgorithm.HS256).build();
    }
}
