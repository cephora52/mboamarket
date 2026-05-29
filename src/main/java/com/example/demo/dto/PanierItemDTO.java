package com.example.demo.dto;

public class PanierItemDTO {
    private Integer idPanierItem;
    private ProduitDTO produit;
    private int quantite;

    public PanierItemDTO() {
    }

    public PanierItemDTO(Integer idPanierItem, ProduitDTO produit, int quantite) {
        this.idPanierItem = idPanierItem;
        this.produit = produit;
        this.quantite = quantite;
    }

    public Integer getIdPanierItem() {
        return idPanierItem;
    }

    public void setIdPanierItem(Integer idPanierItem) {
        this.idPanierItem = idPanierItem;
    }

    public ProduitDTO getProduit() {
        return produit;
    }

    public void setProduit(ProduitDTO produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
