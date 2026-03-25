package com.example.demo.enties;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "paiement")
@NamedQueries({
        @NamedQuery(name = "Paiement.findAll", query = "SELECT p FROM Paiement p"),
        @NamedQuery(name = "Paiement.findByIdPaiement", query = "SELECT p FROM Paiement p WHERE p.idPaiement = :idPaiement"),
        @NamedQuery(name = "Paiement.findByMontant", query = "SELECT p FROM Paiement p WHERE p.montant = :montant"),
        @NamedQuery(name = "Paiement.findByDatePaiement", query = "SELECT p FROM Paiement p WHERE p.datePaiement = :datePaiement")})
public class Paiement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPaiement")
    private Integer idPaiement;
    @Basic(optional = false)
    @Column(name = "montant")
    private double montant;
    @Column(name = "datePaiement")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePaiement;
    @OneToOne(mappedBy = "idPaiement")
    private Recu recu;
    @JoinColumn(name = "idCommande", referencedColumnName = "idCommande")
    @OneToOne
    private Commande idCommande;

    public Paiement() {
    }

    public Paiement(Integer idPaiement) {
        this.idPaiement = idPaiement;
    }

    public Paiement(Integer idPaiement, double montant) {
        this.idPaiement = idPaiement;
        this.montant = montant;
    }

    public Integer getIdPaiement() {
        return idPaiement;
    }

    public void setIdPaiement(Integer idPaiement) {
        this.idPaiement = idPaiement;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Date getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(Date datePaiement) {
        this.datePaiement = datePaiement;
    }

    public Recu getRecu() {
        return recu;
    }

    public void setRecu(Recu recu) {
        this.recu = recu;
    }

    public Commande getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Commande idCommande) {
        this.idCommande = idCommande;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPaiement != null ? idPaiement.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paiement)) {
            return false;
        }
        Paiement other = (Paiement) object;
        if ((this.idPaiement == null && other.idPaiement != null) || (this.idPaiement != null && !this.idPaiement.equals(other.idPaiement))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.mboamarket.Paiement[ idPaiement=" + idPaiement + " ]";
    }

}

