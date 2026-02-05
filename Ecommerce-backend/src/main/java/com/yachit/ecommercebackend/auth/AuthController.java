package com.yachit.ecommercebackend.auth;

import com.yachit.ecommercebackend.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest req) {
        return ResponseEntity.ok(authService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @GetMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestHeader("Authorization") String authHeader) {
        Map<String, Object> response = new HashMap<>();

        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.put("isValid", false);
                response.put("message", "Invalid Authorization header format. Use: Bearer <token>");
                return ResponseEntity.badRequest().body(response);
            }

            String token = authHeader.substring(7); // Remove "Bearer " prefix
            String email = jwtService.extractUsername(token);

            response.put("isValid", true);
            response.put("message", "JWT Token is valid!");
            response.put("email", email);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("isValid", false);
            response.put("message", "JWT Token is invalid or expired: " + e.getMessage());
            return ResponseEntity.status(401).body(response);
        }
    }
}
