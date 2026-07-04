package com.ipd.Gestion_Scolaire.controller;

import com.ipd.Gestion_Scolaire.dto.AuthResponse;
import com.ipd.Gestion_Scolaire.dto.LoginRequest;
import com.ipd.Gestion_Scolaire.dto.RefreshTokenRequest;
import com.ipd.Gestion_Scolaire.dto.RegisterRequest;
import com.ipd.Gestion_Scolaire.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh-token")
    public AuthResponse refreshToken(@RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request);
    }
}