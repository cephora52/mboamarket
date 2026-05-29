package com.example.demo.dto;

public class FavorisDTO {
    private Integer idFavoris;
    private Integer idUtilisateur;
    private Integer idProduit;
    private String nomProduit; // Optionnel pour l'affichage

    public FavorisDTO() {
    }

    public FavorisDTO(Integer idUtilisateur, Integer idProduit) {
        this.idUtilisateur = idUtilisateur;
        this.idProduit = idProduit;
    }

    public Integer getIdFavoris() {
        return idFavoris;
    }

    public void setIdFavoris(Integer idFavoris) {
        this.idFavoris = idFavoris;
    }

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Integer getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Integer idProduit) {
        this.idProduit = idProduit;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }
}
