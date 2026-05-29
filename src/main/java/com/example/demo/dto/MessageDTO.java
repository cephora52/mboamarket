package com.example.demo.dto;

import java.util.Date;

public class MessageDTO {

    private Integer idMessage;
    private String contenu;
    private Date dateEnvoi;
    private String image;
    private boolean read;

    private Integer idConversation;
    private Integer idExpediteur;
    private Integer idDestinataire;

    public MessageDTO() {}

    public Integer getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(Integer idMessage) {
        this.idMessage = idMessage;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(Date dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Integer getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(Integer idConversation) {
        this.idConversation = idConversation;
    }

    public Integer getIdExpediteur() {
        return idExpediteur;
    }

    public void setIdExpediteur(Integer idExpediteur) {
        this.idExpediteur = idExpediteur;
    }

    public Integer getIdDestinataire() {
        return idDestinataire;
    }

    public void setIdDestinataire(Integer idDestinataire) {
        this.idDestinataire = idDestinataire;
    }
}