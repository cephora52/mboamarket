package com.example.demo.enties;


import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.io.Serializable;

@Entity
@Table(name = "conversation")
@NamedQueries({
        @NamedQuery(name = "Conversation.findAll", query = "SELECT c FROM Conversation c"),
        @NamedQuery(name = "Conversation.findByIdConversation", query = "SELECT c FROM Conversation c WHERE c.idConversation = :idConversation"),
        @NamedQuery(name = "Conversation.findByDateCreation", query = "SELECT c FROM Conversation c WHERE c.dateCreation = :dateCreation")})
public class Conversation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idConversation")
    private Integer idConversation;

    @Column(name = "dateCreation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    @ManyToOne
    @JoinColumn(name = "idUser1")
    private Utilisateur user1;

    @ManyToOne
    @JoinColumn(name = "idUser2")
    private Utilisateur user2;

    public Conversation() {
        this.dateCreation = new Date();
    }

    public Conversation(Utilisateur user1, Utilisateur user2) {
        this.user1 = user1;
        this.user2 = user2;
        this.dateCreation = new Date();
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

    public Utilisateur getUser1() {
        return user1;
    }

    public void setUser1(Utilisateur user1) {
        this.user1 = user1;
    }

    public Utilisateur getUser2() {
        return user2;
    }

    public void setUser2(Utilisateur user2) {
        this.user2 = user2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConversation != null ? idConversation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Conversation)) {
            return false;
        }
        Conversation other = (Conversation) object;
        if ((this.idConversation == null && other.idConversation != null) || (this.idConversation != null && !this.idConversation.equals(other.idConversation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.mboamarket.Conversation[ idConversation=" + idConversation + " ]";
    }

    public void setUtilisateurCollection(List<Utilisateur> users) {
    }
}
