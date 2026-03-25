package com.example.demo.dto;

import java.util.Date;

public class ProduitDTO {

    public Integer idProduit;
    public String nomProduit;
    public int qteProduit;
    public double prix;

    public Date datePublication;
    public String statutProduit;

    public String photo;
    public String uniteMesure;
    public String localite;

    // Relations (IDs seulement)
    public Integer idCategorie;
    public Integer idAgriculteur;

    public ProduitDTO(Integer idProduit, String nomProduit, int qteProduit, double prix, Date datePublication, String statutProduit, String photo, String uniteMesure, String localite, Integer idCategorie, Integer idAgriculteur) {
        this.idProduit = idProduit;
        this.nomProduit = nomProduit;
        this.qteProduit = qteProduit;
        this.prix = prix;
        this.datePublication = datePublication;
        this.statutProduit = statutProduit;
        this.photo = photo;
        this.uniteMesure = uniteMesure;
        this.localite = localite;
        this.idCategorie = idCategorie;
        this.idAgriculteur = idAgriculteur;
    }

    public ProduitDTO() {

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

    public int getQteProduit() {
        return qteProduit;
    }

    public void setQteProduit(int qteProduit) {
        this.qteProduit = qteProduit;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Date getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(Date datePublication) {
        this.datePublication = datePublication;
    }

    public String getStatutProduit() {
        return statutProduit;
    }

    public void setStatutProduit(String statutProduit) {
        this.statutProduit = statutProduit;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUniteMesure() {
        return uniteMesure;
    }

    public void setUniteMesure(String uniteMesure) {
        this.uniteMesure = uniteMesure;
    }

    public String getLocalite() {
        return localite;
    }

    public void setLocalite(String localite) {
        this.localite = localite;
    }

    public Integer getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(Integer idCategorie) {
        this.idCategorie = idCategorie;
    }

    public Integer getIdAgriculteur() {
        return idAgriculteur;
    }

    public void setIdAgriculteur(Integer idAgriculteur) {
        this.idAgriculteur = idAgriculteur;
    }
}
