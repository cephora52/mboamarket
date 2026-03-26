package com.example.demo.dto;

import com.example.demo.enums.StatutProduit;

import java.util.Date;

public class ProduitDTO {

    private Integer idProduit;
    private String nomProduit;
    private int qteProduit;
    private double prix;
    private Date datePublication;
    private StatutProduit statutProduit;
    private String photo;
    private String uniteMesure;
    private String localite;
    private Integer idCategorie;
    private Integer idAgriculteur;

    public ProduitDTO() {}

    public Integer getIdProduit() { return idProduit; }
    public void setIdProduit(Integer idProduit) { this.idProduit = idProduit; }

    public String getNomProduit() { return nomProduit; }
    public void setNomProduit(String nomProduit) { this.nomProduit = nomProduit; }

    public int getQteProduit() { return qteProduit; }
    public void setQteProduit(int qteProduit) { this.qteProduit = qteProduit; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    public Date getDatePublication() { return datePublication; }
    public void setDatePublication(Date datePublication) { this.datePublication = datePublication; }

    public StatutProduit getStatutProduit() { return statutProduit; }
    public void setStatutProduit(StatutProduit statutProduit) { this.statutProduit = statutProduit; }

    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }

    public String getUniteMesure() { return uniteMesure; }
    public void setUniteMesure(String uniteMesure) { this.uniteMesure = uniteMesure; }

    public String getLocalite() { return localite; }
    public void setLocalite(String localite) { this.localite = localite; }

    public Integer getIdCategorie() { return idCategorie; }
    public void setIdCategorie(Integer idCategorie) { this.idCategorie = idCategorie; }

    public Integer getIdAgriculteur() { return idAgriculteur; }
    public void setIdAgriculteur(Integer idAgriculteur) { this.idAgriculteur = idAgriculteur; }
}