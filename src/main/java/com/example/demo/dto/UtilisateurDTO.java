package com.example.demo.dto;

public class UtilisateurDTO {

    public Integer idUtilisateur;

    public String nom;
    public String telephone;
    public String ville;

    public String role; // AGRICULTEUR ou DISTRIBUTEUR

    public UtilisateurDTO(Integer idUtilisateur, String nom, String telephone, String ville, String role) {
        this.idUtilisateur = idUtilisateur;
        this.nom = nom;
        this.telephone = telephone;
        this.ville = ville;
        this.role = role;
    }

    public UtilisateurDTO() {

    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
