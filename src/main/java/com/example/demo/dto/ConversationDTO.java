package com.example.demo.dto;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ConversationDTO {

    private Integer idConversation;
    private Date dateCreation;
    private Integer idUser1;
    private Integer idUser2;
    private String otherUserName;
    private String otherUserPhoto;
    private String lastMessage;

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

    public Integer getIdUser1() {
        return idUser1;
    }

    public void setIdUser1(Integer idUser1) {
        this.idUser1 = idUser1;
    }

    public Integer getIdUser2() {
        return idUser2;
    }

    public void setIdUser2(Integer idUser2) {
        this.idUser2 = idUser2;
    }

    public String getOtherUserName() {
        return otherUserName;
    }

    public void setOtherUserName(String otherUserName) {
        this.otherUserName = otherUserName;
    }

    public String getOtherUserPhoto() {
        return otherUserPhoto;
    }

    public void setOtherUserPhoto(String otherUserPhoto) {
        this.otherUserPhoto = otherUserPhoto;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}