package com.example.demo.enties;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "recu")
@NamedQueries({
        @NamedQuery(name = "Recu.findAll", query = "SELECT r FROM Recu r"),
        @NamedQuery(name = "Recu.findByIdRecu", query = "SELECT r FROM Recu r WHERE r.idRecu = :idRecu"),
        @NamedQuery(name = "Recu.findByDateEmission", query = "SELECT r FROM Recu r WHERE r.dateEmission = :dateEmission")})
public class Recu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRecu")
    private Integer idRecu;
    @Column(name = "dateEmission")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEmission;
    @Lob
    @Column(name = "contenu")
    private String contenu;
    @JoinColumn(name = "idPaiement", referencedColumnName = "idPaiement")
    @OneToOne
    private Paiement idPaiement;

    public Recu() {
    }

    public Recu(Integer idRecu) {
        this.idRecu = idRecu;
    }

    public Integer getIdRecu() {
        return idRecu;
    }

    public void setIdRecu(Integer idRecu) {
        this.idRecu = idRecu;
    }

    public Date getDateEmission() {
        return dateEmission;
    }

    public void setDateEmission(Date dateEmission) {
        this.dateEmission = dateEmission;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Paiement getIdPaiement() {
        return idPaiement;
    }

    public void setIdPaiement(Paiement idPaiement) {
        this.idPaiement = idPaiement;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRecu != null ? idRecu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recu)) {
            return false;
        }
        Recu other = (Recu) object;
        if ((this.idRecu == null && other.idRecu != null) || (this.idRecu != null && !this.idRecu.equals(other.idRecu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.mboamarket.Recu[ idRecu=" + idRecu + " ]";
    }

}
