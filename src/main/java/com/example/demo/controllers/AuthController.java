package com.example.demo.controllers;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.UtilisateurDTO;
import com.example.demo.services.implementations.AuthService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    private final AuthService authService;

    // ===== REGISTER =====
    @PostMapping("/register")
    public AuthResponse register(@RequestBody UtilisateurDTO dto) {
        return authService.register(dto);
    }

    // ===== LOGIN =====
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
}