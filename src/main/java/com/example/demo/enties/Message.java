package com.example.demo.enties;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "message")
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMessage;

    @Column(nullable = false)
    private String contenu;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEnvoi;

    @ManyToOne
    @JoinColumn(name = "idConversation")
    private Conversation conversation;

    @ManyToOne
    @JoinColumn(name = "idExpediteur")
    private Utilisateur expediteur;

    @ManyToOne
    @JoinColumn(name = "idDestinataire")
    private Utilisateur destinataire;

    @Column(columnDefinition = "LONGTEXT")
    private String image;

    @Column(name = "lu")
    private boolean lu = false;

    public Message() {
        this.dateEnvoi = new Date();
    }

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

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public Utilisateur getExpediteur() {
        return expediteur;
    }

    public void setExpediteur(Utilisateur expediteur) {
        this.expediteur = expediteur;
    }

    public Utilisateur getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(Utilisateur destinataire) {
        this.destinataire = destinataire;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isLu() {
        return lu;
    }

    public void setLu(boolean lu) {
        this.lu = lu;
    }
}