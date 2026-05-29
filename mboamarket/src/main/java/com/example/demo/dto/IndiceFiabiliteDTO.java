package com.example.demo.dto;

public class IndiceFiabiliteDTO {

    public Integer idIndice;

    public Double tauxCommande;
    public Double regularitePublication;

    public String niveauFiabilite;

    // Relation
    public Integer idAgriculteur;

    public IndiceFiabiliteDTO(Integer idIndice, Double tauxCommande, Double regularitePublication, String niveauFiabilite, Integer idAgriculteur) {
        this.idIndice = idIndice;
        this.tauxCommande = tauxCommande;
        this.regularitePublication = regularitePublication;
        this.niveauFiabilite = niveauFiabilite;
        this.idAgriculteur = idAgriculteur;
    }

    public IndiceFiabiliteDTO() {

    }

    public Integer getIdIndice() {
        return idIndice;
    }

    public void setIdIndice(Integer idIndice) {
        this.idIndice = idIndice;
    }

    public Double getTauxCommande() {
        return tauxCommande;
    }

    public void setTauxCommande(Double tauxCommande) {
        this.tauxCommande = tauxCommande;
    }

    public Double getRegularitePublication() {
        return regularitePublication;
    }

    public void setRegularitePublication(Double regularitePublication) {
        this.regularitePublication = regularitePublication;
    }

    public String getNiveauFiabilite() {
        return niveauFiabilite;
    }

    public void setNiveauFiabilite(String niveauFiabilite) {
        this.niveauFiabilite = niveauFiabilite;
    }

    public Integer getIdAgriculteur() {
        return idAgriculteur;
    }

    public void setIdAgriculteur(Integer idAgriculteur) {
        this.idAgriculteur = idAgriculteur;
    }
}
