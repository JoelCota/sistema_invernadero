package com.invernadero.auth_service.controller;

import com.invernadero.auth_service.model.AuthRequest;
import com.invernadero.auth_service.model.AuthResponse;
import com.invernadero.auth_service.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse response = authService.login(request);
        System.out.println(response.toString());
        return ResponseEntity.ok(new AuthResponse(response.getToken()));
    }
}
