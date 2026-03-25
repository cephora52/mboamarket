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
    @Basic(optional = false)
    @Column(name = "idConversation")
    private Integer idConversation;
    @Column(name = "dateCreation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    @ManyToMany(mappedBy = "conversationCollection")
    private List<Utilisateur> utilisateurCollection;

    public Conversation() {
    }

    public Conversation(Integer idConversation) {
        this.idConversation = idConversation;
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

    public List<Utilisateur> getUtilisateurCollection() {
        return utilisateurCollection;
    }

    public void setUtilisateurCollection(List<Utilisateur> utilisateurCollection) {
        this.utilisateurCollection = utilisateurCollection;
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

}
