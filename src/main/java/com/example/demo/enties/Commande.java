package com.example.demo.enties;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "commande")
@NamedQueries({
        @NamedQuery(name = "Commande.findAll", query = "SELECT c FROM Commande c"),
        @NamedQuery(name = "Commande.findByIdCommande", query = "SELECT c FROM Commande c WHERE c.idCommande = :idCommande"),
        @NamedQuery(name = "Commande.findByDateCommande", query = "SELECT c FROM Commande c WHERE c.dateCommande = :dateCommande"),
        @NamedQuery(name = "Commande.findByQteCommande", query = "SELECT c FROM Commande c WHERE c.qteCommande = :qteCommande"),
        @NamedQuery(name = "Commande.findByMontantTotal", query = "SELECT c FROM Commande c WHERE c.montantTotal = :montantTotal"),
        @NamedQuery(name = "Commande.findByStatutCmd", query = "SELECT c FROM Commande c WHERE c.statutCmd = :statutCmd")})
public class Commande implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCommande")
    private Integer idCommande;
    @Column(name = "dateCommande")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCommande;
    @Basic(optional = false)
    @Column(name = "qte_commande")
    private int qteCommande;
    @Basic(optional = false)
    @Column(name = "montantTotal")
    private double montantTotal;
    @Column(name = "statutCmd")
    private String statutCmd;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "commande")
    private List<CommandeProduit> commandeProduitCollection;
    @OneToOne(mappedBy = "idCommande")
    private Paiement paiement;
    @JoinColumn(name = "idDistributeur", referencedColumnName = "idUtilisateur")
    @ManyToOne(optional = false)
    private Utilisateur idDistributeur;

    public Commande() {
    }

    public Commande(Integer idCommande) {
        this.idCommande = idCommande;
    }

    public Commande(Integer idCommande, int qteCommande, double montantTotal) {
        this.idCommande = idCommande;
        this.qteCommande = qteCommande;
        this.montantTotal = montantTotal;
    }

    public Integer getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Integer idCommande) {
        this.idCommande = idCommande;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public int getQteCommande() {
        return qteCommande;
    }

    public void setQteCommande(int qteCommande) {
        this.qteCommande = qteCommande;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public String getStatutCmd() {
        return statutCmd;
    }

    public void setStatutCmd(String statutCmd) {
        this.statutCmd = statutCmd;
    }

    public List<CommandeProduit> getCommandeProduitCollection() {
        return commandeProduitCollection;
    }

    public void setCommandeProduitCollection(List<CommandeProduit> commandeProduitCollection) {
        this.commandeProduitCollection = commandeProduitCollection;
    }

    public Paiement getPaiement() {
        return paiement;
    }

    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
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
        hash += (idCommande != null ? idCommande.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Commande)) {
            return false;
        }
        Commande other = (Commande) object;
        if ((this.idCommande == null && other.idCommande != null) || (this.idCommande != null && !this.idCommande.equals(other.idCommande))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.mboamarket.Commande[ idCommande=" + idCommande + " ]";
    }

}
