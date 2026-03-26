package com.example.demo.enties;

import com.example.demo.enums.StatutCommande;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "commande")
public class Commande implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCommande;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCommande;

    private double montantTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "statutCmd")
    private StatutCommande statutCmd;

    @OneToMany(mappedBy = "commande")
    private List<CommandeProduit> commandeProduitCollection;

    @OneToOne(mappedBy = "idCommande")
    private Paiement paiement;

    @ManyToOne
    @JoinColumn(name = "idDistributeur")
    private Utilisateur idDistributeur;

    public Commande() {
    }

    public Commande(Integer idCommande,
                    Date dateCommande,
                    double montantTotal,
                    StatutCommande statutCmd,
                    Utilisateur idDistributeur) {

        this.idCommande = idCommande;
        this.dateCommande = dateCommande;
        this.montantTotal = montantTotal;
        this.statutCmd = statutCmd;
        this.idDistributeur = idDistributeur;
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

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public StatutCommande getStatutCmd() {
        return statutCmd;
    }

    public void setStatutCmd(StatutCommande statutCmd) {
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
}