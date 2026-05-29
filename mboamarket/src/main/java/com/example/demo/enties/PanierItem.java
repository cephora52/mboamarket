package com.example.demo.enties;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "panier_item")
public class PanierItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPanierItem;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idUtilisateur", referencedColumnName = "idUtilisateur")
    private Utilisateur utilisateur;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idProduit", referencedColumnName = "idProduit")
    private Produit produit;

    @Column(nullable = false)
    private int quantite;

    public PanierItem() {
    }

    public PanierItem(Utilisateur utilisateur, Produit produit, int quantite) {
        this.utilisateur = utilisateur;
        this.produit = produit;
        this.quantite = quantite;
    }

    public Integer getIdPanierItem() {
        return idPanierItem;
    }

    public void setIdPanierItem(Integer idPanierItem) {
        this.idPanierItem = idPanierItem;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
