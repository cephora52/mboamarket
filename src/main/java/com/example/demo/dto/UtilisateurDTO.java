package com.example.demo.dto;

import com.example.demo.enums.Role;

public class UtilisateurDTO {

    private Integer idUtilisateur;

    private String nom;
    private String telephone;
    private String ville;

    private String email;
    private String password;

    private Role role;

    // ===== CONSTRUCTEURS =====

    public UtilisateurDTO() {
    }

    public UtilisateurDTO(Integer idUtilisateur, String nom, String telephone, String ville,
                          String email, String password, Role role) {
        this.idUtilisateur = idUtilisateur;
        this.nom = nom;
        this.telephone = telephone;
        this.ville = ville;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // ===== GETTERS & SETTERS =====

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}