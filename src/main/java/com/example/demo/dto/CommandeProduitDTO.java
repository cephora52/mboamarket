package com.example.demo.dto;

public class CommandeProduitDTO {

    public Integer idCommande;
    public Integer idProduit;

    public int quantite;

    public CommandeProduitDTO() {

    }

    public CommandeProduitDTO(Integer idCommande, Integer idProduit, int quantite) {
        this.idCommande = idCommande;
        this.idProduit = idProduit;
        this.quantite = quantite;
    }

    public Integer getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Integer idCommande) {
        this.idCommande = idCommande;
    }

    public Integer getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Integer idProduit) {
        this.idProduit = idProduit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
