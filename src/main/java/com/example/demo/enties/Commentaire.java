package com.example.demo.enties;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "commentaire")
@NamedQueries({
        @NamedQuery(name = "Commentaire.findAll", query = "SELECT c FROM Commentaire c"),
        @NamedQuery(name = "Commentaire.findByIdCommentaire", query = "SELECT c FROM Commentaire c WHERE c.idCommentaire = :idCommentaire")})
public class Commentaire implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCommentaire")
    private Integer idCommentaire;
    @Basic(optional = false)
    @Lob
    @Column(name = "contenu")
    private String contenu;
    @JoinColumn(name = "idProduit", referencedColumnName = "idProduit")
    @ManyToOne(optional = false)
    private Produit idProduit;
    @JoinColumn(name = "idDistributeur", referencedColumnName = "idUtilisateur")
    @ManyToOne(optional = false)
    private Utilisateur idDistributeur;

    public Commentaire() {
    }

    public Commentaire(Integer idCommentaire) {
        this.idCommentaire = idCommentaire;
    }

    public Commentaire(Integer idCommentaire, String contenu) {
        this.idCommentaire = idCommentaire;
        this.contenu = contenu;
    }

    public Integer getIdCommentaire() {
        return idCommentaire;
    }

    public void setIdCommentaire(Integer idCommentaire) {
        this.idCommentaire = idCommentaire;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Produit getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Produit idProduit) {
        this.idProduit = idProduit;
    }

    public Utilisateur getIdDistributeur() {
        return idDistributeur;
    }

    public void setIdDistributeur(Utilisateur idDistributeur) {
        this.idDistributeur = idDistributeur;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCommentaire != null ? idCommentaire.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Commentaire)) {
            return false;
        }
        Commentaire other = (Commentaire) object;
        if ((this.idCommentaire == null && other.idCommentaire != null) || (this.idCommentaire != null && !this.idCommentaire.equals(other.idCommentaire))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.mboamarket.Commentaire[ idCommentaire=" + idCommentaire + " ]";
    }

    public Date getDateCommentaire() {
        return null;
    }

    public void setDateCommentaire(Date dateCommentaire) {
    }
}