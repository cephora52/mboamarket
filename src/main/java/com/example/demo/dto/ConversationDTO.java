package com.example.demo.dto;

import java.util.Date;
import java.util.List;

public class ConversationDTO {

    private Integer idConversation;
    private Date dateCreation;
    private List<Integer> participants;

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

    public List<Integer> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Integer> participants) {
        this.participants = participants;
    }
}