package com.example.demo.dto;

import java.util.Date;

public class PaiementDTO {

    public Integer idPaiement;

    public double montant;
    public Date datePaiement;

    // Relation
    public Integer idCommande;

    public PaiementDTO(Integer idPaiement, double montant, Date datePaiement, Integer idCommande) {
        this.idPaiement = idPaiement;
        this.montant = montant;
        this.datePaiement = datePaiement;
        this.idCommande = idCommande;
    }

    public PaiementDTO() {
    }

    public Integer getIdPaiement() {
        return idPaiement;
    }

    public void setIdPaiement(Integer idPaiement) {
        this.idPaiement = idPaiement;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Date getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(Date datePaiement) {
        this.datePaiement = datePaiement;
    }

    public Integer getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Integer idCommande) {
        this.idCommande = idCommande;
    }
}
