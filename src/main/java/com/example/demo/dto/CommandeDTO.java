package com.example.demo.dto;

import com.example.demo.enums.StatutCommande;
import java.util.Date;

public class CommandeDTO {

    private Integer idCommande;
    private Date dateCommande;
    private double montantTotal;
    private StatutCommande statutCmd;
    private Integer idDistributeur;

    public CommandeDTO() {
    }

    public CommandeDTO(Integer idCommande, Date dateCommande,
                       double montantTotal,
                       StatutCommande statutCmd,
                       Integer idDistributeur) {

        this.idCommande = idCommande;
        this.dateCommande = dateCommande;
        this.montantTotal = montantTotal;
        this.statutCmd = statutCmd;
        this.idDistributeur = idDistributeur;
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

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public StatutCommande getStatutCmd() {
        return statutCmd;
    }

    public void setStatutCmd(StatutCommande statutCmd) {
        this.statutCmd = statutCmd;
    }

    public Integer getIdDistributeur() {
        return idDistributeur;
    }

    public void setIdDistributeur(Integer idDistributeur) {
        this.idDistributeur = idDistributeur;
    }
}