package com.example.demo.dto;

public class AuthResponse {

    private String message;
    private String role;

    // ✅ constructeur pour login
    public AuthResponse(String message, String role) {
        this.message = message;
        this.role = role;
    }

    // ✅ constructeur pour register (sans role)
    public AuthResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getRole() {
        return role;
    }
}