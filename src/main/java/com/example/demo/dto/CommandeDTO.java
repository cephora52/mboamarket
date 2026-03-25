package com.example.demo.dto;

import java.util.Date;

public class CommandeDTO {

    public Integer idCommande;
    public Date dateCommande;

    public int qteCommande;
    public double montantTotal;

    public String statutCmd;

    // Relation
    public Integer idDistributeur;

    public CommandeDTO(Integer idCommande, Date dateCommande, int qteCommande, double montantTotal, String statutCmd, Integer idDistributeur) {
        this.idCommande = idCommande;
        this.dateCommande = dateCommande;
        this.qteCommande = qteCommande;
        this.montantTotal = montantTotal;
        this.statutCmd = statutCmd;
        this.idDistributeur = idDistributeur;
    }

    public CommandeDTO() {
    }

    public Integer getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Integer idCommande) {
        this.idCommande = idCommande;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public int getQteCommande() {
        return qteCommande;
    }

    public void setQteCommande(int qteCommande) {
        this.qteCommande = qteCommande;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public String getStatutCmd() {
        return statutCmd;
    }

    public void setStatutCmd(String statutCmd) {
        this.statutCmd = statutCmd;
    }

    public Integer getIdDistributeur() {
        return idDistributeur;
    }

    public void setIdDistributeur(Integer idDistributeur) {
        this.idDistributeur = idDistributeur;
    }
}
