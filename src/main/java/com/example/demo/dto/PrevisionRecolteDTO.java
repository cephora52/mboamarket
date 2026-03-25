package com.example.demo.dto;

import java.util.Date;

public class PrevisionRecolteDTO {

    public Integer idPrevision;

    public Date dateDisponibilite;
    public int qteEstime;

    // Relation
    public Integer idProduit;

    public PrevisionRecolteDTO(Integer idPrevision, Date dateDisponibilite, int qteEstime, Integer idProduit) {
        this.idPrevision = idPrevision;
        this.dateDisponibilite = dateDisponibilite;
        this.qteEstime = qteEstime;
        this.idProduit = idProduit;
    }

    public PrevisionRecolteDTO() {

    }

    public Integer getIdPrevision() {
        return idPrevision;
    }

    public void setIdPrevision(Integer idPrevision) {
        this.idPrevision = idPrevision;
    }

    public Date getDateDisponibilite() {
        return dateDisponibilite;
    }

    public void setDateDisponibilite(Date dateDisponibilite) {
        this.dateDisponibilite = dateDisponibilite;
    }

    public int getQteEstime() {
        return qteEstime;
    }

    public void setQteEstime(int qteEstime) {
        this.qteEstime = qteEstime;
    }

    public Integer getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Integer idProduit) {
        this.idProduit = idProduit;
    }
}
