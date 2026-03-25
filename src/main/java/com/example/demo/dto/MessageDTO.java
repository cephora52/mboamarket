package com.example.demo.dto;

import java.util.Date;

public class MessageDTO {

    private Integer idMessage;
    private String contenu;
    private Date dateEnvoi;

    private Integer idConversation;
    private Integer idExpediteur;

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
}