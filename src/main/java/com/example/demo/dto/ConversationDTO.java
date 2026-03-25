package com.example.demo.dto;

import java.util.Date;

public class ConversationDTO {

    public Integer idConversation;
    public Date dateCreation;

    public ConversationDTO(Integer idConversation, Date dateCreation) {
        this.idConversation = idConversation;
        this.dateCreation = dateCreation;
    }

    public ConversationDTO() {

    }

    public Integer getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(Integer idConversation) {
        this.idConversation = idConversation;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
}
