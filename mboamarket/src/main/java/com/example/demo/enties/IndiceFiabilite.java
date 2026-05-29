package com.example.demo.enties;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "indice_fiabilite")
@NamedQueries({
        @NamedQuery(name = "IndiceFiabilite.findAll", query = "SELECT i FROM IndiceFiabilite i"),
        @NamedQuery(name = "IndiceFiabilite.findByIdIndice", query = "SELECT i FROM IndiceFiabilite i WHERE i.idIndice = :idIndice"),
        @NamedQuery(name = "IndiceFiabilite.findByTauxCommande", query = "SELECT i FROM IndiceFiabilite i WHERE i.tauxCommande = :tauxCommande"),
        @NamedQuery(name = "IndiceFiabilite.findByRegularitePublication", query = "SELECT i FROM IndiceFiabilite i WHERE i.regularitePublication = :regularitePublication"),
        @NamedQuery(name = "IndiceFiabilite.findByNiveauFiabilite", query = "SELECT i FROM IndiceFiabilite i WHERE i.niveauFiabilite = :niveauFiabilite")})
public class IndiceFiabilite implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idIndice")
    private Integer idIndice;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tauxCommande")
    private Double tauxCommande;
    @Column(name = "regularitePublication")
    private Double regularitePublication;
    @Column(name = "niveauFiabilite")
    private String niveauFiabilite;
    @JoinColumn(name = "idAgriculteur", referencedColumnName = "idUtilisateur")
    @OneToOne
    private Utilisateur idAgriculteur;

    public IndiceFiabilite() {
    }

    public IndiceFiabilite(Integer idIndice) {
        this.idIndice = idIndice;
    }

    public Integer getIdIndice() {
        return idIndice;
    }

    public void setIdIndice(Integer idIndice) {
        this.idIndice = idIndice;
    }

    public Double getTauxCommande() {
        return tauxCommande;
    }

    public void setTauxCommande(Double tauxCommande) {
        this.tauxCommande = tauxCommande;
    }

    public Double getRegularitePublication() {
        return regularitePublication;
    }

    public void setRegularitePublication(Double regularitePublication) {
        this.regularitePublication = regularitePublication;
    }

    public String getNiveauFiabilite() {
        return niveauFiabilite;
    }

    public void setNiveauFiabilite(String niveauFiabilite) {
        this.niveauFiabilite = niveauFiabilite;
    }

    public Utilisateur getIdAgriculteur() {
        return idAgriculteur;
    }

    public void setIdAgriculteur(Utilisateur idAgriculteur) {
        this.idAgriculteur = idAgriculteur;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idIndice != null ? idIndice.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IndiceFiabilite)) {
            return false;
        }
        IndiceFiabilite other = (IndiceFiabilite) object;
        if ((this.idIndice == null && other.idIndice != null) || (this.idIndice != null && !this.idIndice.equals(other.idIndice))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.mboamarket.IndiceFiabilite[ idIndice=" + idIndice + " ]";
    }

}
