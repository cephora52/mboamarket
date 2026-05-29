package com.example.demo.enties;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "favoris")
public class Favoris implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFavoris;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idUtilisateur", referencedColumnName = "idUtilisateur")
    private Utilisateur utilisateur;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idProduit", referencedColumnName = "idProduit")
    private Produit produit;

    public Favoris() {
    }

    public Favoris(Utilisateur utilisateur, Produit produit) {
        this.utilisateur = utilisateur;
        this.produit = produit;
    }

    public Integer getIdFavoris() {
        return idFavoris;
    }

    public void setIdFavoris(Integer idFavoris) {
        this.idFavoris = idFavoris;
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
}
