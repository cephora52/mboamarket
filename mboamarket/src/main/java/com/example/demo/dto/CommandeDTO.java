package com.example.demo.dto;

import com.example.demo.enums.StatutCommande;
import java.util.Date;

public class CommandeDTO {

    private Integer idCommande;
    private Date dateCommande;
    private double montantTotal;
    private StatutCommande statutCmd;
    private Integer idDistributeur;
    private Integer idAgriculteur;
    private String nomAgriculteur;
    private String nomDistributeur;
    private String codeConfirmation;
    private Integer quantiteLivree;
    private java.util.List<CommandeProduitDTO> items;

    public CommandeDTO() {
    }

    public CommandeDTO(Integer idCommande, Date dateCommande,
                       double montantTotal,
                       StatutCommande statutCmd,
                       Integer idDistributeur,
                       Integer idAgriculteur) {

        this.idCommande = idCommande;
        this.dateCommande = dateCommande;
        this.montantTotal = montantTotal;
        this.statutCmd = statutCmd;
        this.idDistributeur = idDistributeur;
        this.idAgriculteur = idAgriculteur;
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

    public Integer getIdAgriculteur() {
        return idAgriculteur;
    }

    public void setIdAgriculteur(Integer idAgriculteur) {
        this.idAgriculteur = idAgriculteur;
    }

    public String getNomAgriculteur() {
        return nomAgriculteur;
    }

    public void setNomAgriculteur(String nomAgriculteur) {
        this.nomAgriculteur = nomAgriculteur;
    }

    public java.util.List<CommandeProduitDTO> getItems() {
        return items;
    }

    public void setItems(java.util.List<CommandeProduitDTO> items) {
        this.items = items;
    }

    public String getNomDistributeur() {
        return nomDistributeur;
    }

    public void setNomDistributeur(String nomDistributeur) {
        this.nomDistributeur = nomDistributeur;
    }

    public String getCodeConfirmation() {
        return codeConfirmation;
    }

    public void setCodeConfirmation(String codeConfirmation) {
        this.codeConfirmation = codeConfirmation;
    }

    public Integer getQuantiteLivree() {
        return quantiteLivree;
    }

    public void setQuantiteLivree(Integer quantiteLivree) {
        this.quantiteLivree = quantiteLivree;
    }
}
