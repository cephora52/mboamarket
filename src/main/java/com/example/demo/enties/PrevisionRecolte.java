package com.example.demo.enties;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "prevision_recolte")
@NamedQueries({
        @NamedQuery(name = "PrevisionRecolte.findAll", query = "SELECT p FROM PrevisionRecolte p"),
        @NamedQuery(name = "PrevisionRecolte.findByIdPrevision", query = "SELECT p FROM PrevisionRecolte p WHERE p.idPrevision = :idPrevision"),
        @NamedQuery(name = "PrevisionRecolte.findByDateDisponibilite", query = "SELECT p FROM PrevisionRecolte p WHERE p.dateDisponibilite = :dateDisponibilite"),
        @NamedQuery(name = "PrevisionRecolte.findByQteEstime", query = "SELECT p FROM PrevisionRecolte p WHERE p.qteEstime = :qteEstime")})
public class PrevisionRecolte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPrevision")
    private Integer idPrevision;
    @Basic(optional = false)
    @Column(name = "dateDisponibilite")
    @Temporal(TemporalType.DATE)
    private Date dateDisponibilite;
    @Basic(optional = false)
    @Column(name = "qteEstime")
    private int qteEstime;
    @JoinColumn(name = "idProduit", referencedColumnName = "idProduit")
    @ManyToOne(optional = false)
    private Produit idProduit;

    public PrevisionRecolte() {
    }

    public PrevisionRecolte(Integer idPrevision) {
        this.idPrevision = idPrevision;
    }

    public PrevisionRecolte(Integer idPrevision, Date dateDisponibilite, int qteEstime) {
        this.idPrevision = idPrevision;
        this.dateDisponibilite = dateDisponibilite;
        this.qteEstime = qteEstime;
    }

    public Integer getIdPrevision() {
        return idPrevision;
    }

    public void setIdPrevision(Integer idPrevision) {
        this.idPrevision = idPrevision;
    }

    public Date getDateDisponibilite() {
        return dateDisponibilite;
    }

    public void setDateDisponibilite(Date dateDisponibilite) {
        this.dateDisponibilite = dateDisponibilite;
    }

    public int getQteEstime() {
        return qteEstime;
    }

    public void setQteEstime(int qteEstime) {
        this.qteEstime = qteEstime;
    }

    public Produit getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Produit idProduit) {
        this.idProduit = idProduit;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrevision != null ? idPrevision.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrevisionRecolte)) {
            return false;
        }
        PrevisionRecolte other = (PrevisionRecolte) object;
        if ((this.idPrevision == null && other.idPrevision != null) || (this.idPrevision != null && !this.idPrevision.equals(other.idPrevision))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.mboamarket.PrevisionRecolte[ idPrevision=" + idPrevision + " ]";
    }

}
