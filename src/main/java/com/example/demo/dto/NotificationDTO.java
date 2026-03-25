package com.example.demo.dto;

import java.util.Date;

public class NotificationDTO {

    public Integer idNotification;

    public String contenuNotification;
    public Date dateNotification;

    public Boolean lue;

    // Relation
    public Integer idUtilisateur;

    public NotificationDTO(Integer idNotification, String contenuNotification, Date dateNotification, Boolean lue, Integer idUtilisateur) {
        this.idNotification = idNotification;
        this.contenuNotification = contenuNotification;
        this.dateNotification = dateNotification;
        this.lue = lue;
        this.idUtilisateur = idUtilisateur;
    }

    public NotificationDTO() {

    }

    public Integer getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(Integer idNotification) {
        this.idNotification = idNotification;
    }

    public String getContenuNotification() {
        return contenuNotification;
    }

    public void setContenuNotification(String contenuNotification) {
        this.contenuNotification = contenuNotification;
    }

    public Date getDateNotification() {
        return dateNotification;
    }

    public void setDateNotification(Date dateNotification) {
        this.dateNotification = dateNotification;
    }

    public Boolean getLue() {
        return lue;
    }

    public void setLue(Boolean lue) {
        this.lue = lue;
    }

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
}
