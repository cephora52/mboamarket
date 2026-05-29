package com.example.demo.enties;


import jakarta.persistence.*;
import java.util.List;
import java.io.Serializable;

@Entity
@Table(name = "commande_produit")
@NamedQueries({
        @NamedQuery(name = "CommandeProduit.findAll", query = "SELECT c FROM CommandeProduit c"),
        @NamedQuery(name = "CommandeProduit.findByIdCommande", query = "SELECT c FROM CommandeProduit c WHERE c.commandeProduitPK.idCommande = :idCommande"),
        @NamedQuery(name = "CommandeProduit.findByIdProduit", query = "SELECT c FROM CommandeProduit c WHERE c.commandeProduitPK.idProduit = :idProduit"),
        @NamedQuery(name = "CommandeProduit.findByQuantite", query = "SELECT c FROM CommandeProduit c WHERE c.quantite = :quantite")})
public class CommandeProduit implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CommandeProduitPK commandeProduitPK;
    @Basic(optional = false)
    @Column(name = "quantite")
    private int quantite;
    @JoinColumn(name = "idCommande", referencedColumnName = "idCommande", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Commande commande;
    @JoinColumn(name = "idProduit", referencedColumnName = "idProduit", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Produit produit;

    public CommandeProduit() {
    }

    public CommandeProduit(CommandeProduitPK commandeProduitPK) {
        this.commandeProduitPK = commandeProduitPK;
    }

    public CommandeProduit(CommandeProduitPK commandeProduitPK, int quantite) {
        this.commandeProduitPK = commandeProduitPK;
        this.quantite = quantite;
    }

    public CommandeProduit(int idCommande, int idProduit) {
        this.commandeProduitPK = new CommandeProduitPK(idCommande, idProduit);
    }

    public CommandeProduitPK getCommandeProduitPK() {
        return commandeProduitPK;
    }

    public void setCommandeProduitPK(CommandeProduitPK commandeProduitPK) {
        this.commandeProduitPK = commandeProduitPK;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (commandeProduitPK != null ? commandeProduitPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CommandeProduit)) {
            return false;
        }
        CommandeProduit other = (CommandeProduit) object;
        if ((this.commandeProduitPK == null && other.commandeProduitPK != null) || (this.commandeProduitPK != null && !this.commandeProduitPK.equals(other.commandeProduitPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.mboamarket.CommandeProduit[ commandeProduitPK=" + commandeProduitPK + " ]";
    }

}
