package com.example.demo.dto;

public class AuthResponse {

    private Integer id;
    private String nom;
    private String email;
    private String message;
    private String role;

    // ✅ constructeur pour login (avec détails complets)
    public AuthResponse(String message, String role, Integer id, String nom, String email) {
        this.message = message;
        this.role = role;
        this.id = id;
        this.nom = nom;
        this.email = email;
    }

    // ✅ constructeur pour register
    public AuthResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getRole() {
        return role;
    }

    public Integer getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }
}