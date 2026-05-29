package com.example.demo.dto;

import java.util.Date;

public class NotificationDTO {
    private Integer idNotification;
    private String titre;
    private String message;
    private Date dateNotification;
    private boolean lu;
    private Integer idUtilisateur;

    public NotificationDTO() {
    }

    public NotificationDTO(Integer idNotification, String titre, String message, Date dateNotification, boolean lu, Integer idUtilisateur) {
        this.idNotification = idNotification;
        this.titre = titre;
        this.message = message;
        this.dateNotification = dateNotification;
        this.lu = lu;
        this.idUtilisateur = idUtilisateur;
    }

    // Getters and Setters
    public Integer getIdNotification() { return idNotification; }
    public void setIdNotification(Integer idNotification) { this.idNotification = idNotification; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Date getDateNotification() { return dateNotification; }
    public void setDateNotification(Date dateNotification) { this.dateNotification = dateNotification; }

    public boolean isLu() { return lu; }
    public void setLu(boolean lu) { this.lu = lu; }

    public Integer getIdUtilisateur() { return idUtilisateur; }
    public void setIdUtilisateur(Integer idUtilisateur) { this.idUtilisateur = idUtilisateur; }
}
