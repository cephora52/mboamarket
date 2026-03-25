package com.example.demo.dto;

import java.util.Date;

public class RecuDTO {

    public Integer idRecu;

    public Date dateEmission;
    public String contenu;

    // Relation
    public Integer idPaiement;

    public RecuDTO(Integer idRecu, Date dateEmission, String contenu, Integer idPaiement) {
        this.idRecu = idRecu;
        this.dateEmission = dateEmission;
        this.contenu = contenu;
        this.idPaiement = idPaiement;
    }

    public RecuDTO() {

    }

    public Integer getIdRecu() {
        return idRecu;
    }

    public void setIdRecu(Integer idRecu) {
        this.idRecu = idRecu;
    }

    public Date getDateEmission() {
        return dateEmission;
    }

    public void setDateEmission(Date dateEmission) {
        this.dateEmission = dateEmission;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Integer getIdPaiement() {
        return idPaiement;
    }

    public void setIdPaiement(Integer idPaiement) {
        this.idPaiement = idPaiement;
    }
}
