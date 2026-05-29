package com.example.demo.dto;

public class CartItemDTO {
    private Integer idProduit;
    private int quantite;

    public CartItemDTO() {
    }

    public CartItemDTO(Integer idProduit, int quantite) {
        this.idProduit = idProduit;
        this.quantite = quantite;
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
