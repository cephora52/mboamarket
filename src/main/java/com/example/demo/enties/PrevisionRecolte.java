package com.example.demo.enties;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "prevision_recolte")
public class PrevisionRecolte implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPrevision;

    private String nomProduitPrevu;

    private int qteEstime;

    @Temporal(TemporalType.DATE)
    private Date dateDisponibilite;

    @ManyToOne
    @JoinColumn(name = "idAgriculteur")
    private Utilisateur agriculteur;

    public PrevisionRecolte() {}

    public Integer getIdPrevision() {
        return idPrevision;
    }

    public void setIdPrevision(Integer idPrevision) {
        this.idPrevision = idPrevision;
    }

    public String getNomProduitPrevu() {
        return nomProduitPrevu;
    }

    public void setNomProduitPrevu(String nomProduitPrevu) {
        this.nomProduitPrevu = nomProduitPrevu;
    }

    public int getQteEstime() {
        return qteEstime;
    }

    public void setQteEstime(int qteEstime) {
        this.qteEstime = qteEstime;
    }

    public Date getDateDisponibilite() {
        return dateDisponibilite;
    }

    public void setDateDisponibilite(Date dateDisponibilite) {
        this.dateDisponibilite = dateDisponibilite;
    }

    public Utilisateur getAgriculteur() {
        return agriculteur;
    }

    public void setAgriculteur(Utilisateur agriculteur) {
        this.agriculteur = agriculteur;
    }
}