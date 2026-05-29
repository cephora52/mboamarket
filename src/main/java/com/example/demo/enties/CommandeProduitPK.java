package com.example.demo.enties;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;

@Embeddable
public class CommandeProduitPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idCommande")
    private int idCommande;
    @Basic(optional = false)
    @Column(name = "idProduit")
    private int idProduit;

    public CommandeProduitPK() {
    }

    public CommandeProduitPK(int idCommande, int idProduit) {
        this.idCommande = idCommande;
        this.idProduit = idProduit;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idCommande;
        hash += (int) idProduit;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CommandeProduitPK)) {
            return false;
        }
        CommandeProduitPK other = (CommandeProduitPK) object;
        if (this.idCommande != other.idCommande) {
            return false;
        }
        if (this.idProduit != other.idProduit) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.mboamarket.CommandeProduitPK[ idCommande=" + idCommande + ", idProduit=" + idProduit + " ]";
    }

}