package com.example.demo.enties;


import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "produit")
@NamedQueries({
        @NamedQuery(name = "Produit.findAll", query = "SELECT p FROM Produit p"),
        @NamedQuery(name = "Produit.findByIdProduit", query = "SELECT p FROM Produit p WHERE p.idProduit = :idProduit"),
        @NamedQuery(name = "Produit.findByNomProduit", query = "SELECT p FROM Produit p WHERE p.nomProduit = :nomProduit"),
        @NamedQuery(name = "Produit.findByQteProduit", query = "SELECT p FROM Produit p WHERE p.qteProduit = :qteProduit"),
        @NamedQuery(name = "Produit.findByPrix", query = "SELECT p FROM Produit p WHERE p.prix = :prix"),
        @NamedQuery(name = "Produit.findByDatePublication", query = "SELECT p FROM Produit p WHERE p.datePublication = :datePublication"),
        @NamedQuery(name = "Produit.findByStatutProduit", query = "SELECT p FROM Produit p WHERE p.statutProduit = :statutProduit"),
        @NamedQuery(name = "Produit.findByPhoto", query = "SELECT p FROM Produit p WHERE p.photo = :photo"),
        @NamedQuery(name = "Produit.findByUniteMesure", query = "SELECT p FROM Produit p WHERE p.uniteMesure = :uniteMesure"),
        @NamedQuery(name = "Produit.findByLocalite", query = "SELECT p FROM Produit p WHERE p.localite = :localite")})
public class Produit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idProduit")
    private Integer idProduit;
    @Basic(optional = false)
    @Column(name = "nomProduit")
    private String nomProduit;
    @Basic(optional = false)
    @Column(name = "qteProduit")
    private int qteProduit;
    @Basic(optional = false)
    @Column(name = "prix")
    private double prix;
    @Column(name = "datePublication")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePublication;
    @Column(name = "statutProduit")
    private String statutProduit;
    @Column(name = "photo")
    private String photo;
    @Column(name = "uniteMesure")
    private String uniteMesure;
    @Column(name = "localite")
    private String localite;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "produit")
    private List<CommandeProduit> commandeProduitCollection;
    @JoinColumn(name = "idCategorie", referencedColumnName = "idCategorie")
    @ManyToOne(optional = false)
    private Categorie idCategorie;
    @JoinColumn(name = "idAgriculteur", referencedColumnName = "idUtilisateur")
    @ManyToOne(optional = false)
    private Utilisateur idAgriculteur;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProduit")
    private List<PrevisionRecolte> previsionRecolteCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProduit")
    private List<Commentaire> commentaireCollection;

    public Produit() {
    }

    public Produit(Integer idProduit) {
        this.idProduit = idProduit;
    }

    public Produit(Integer idProduit, String nomProduit, int qteProduit, double prix) {
        this.idProduit = idProduit;
        this.nomProduit = nomProduit;
        this.qteProduit = qteProduit;
        this.prix = prix;
    }

    public Integer getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Integer idProduit) {
        this.idProduit = idProduit;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public int getQteProduit() {
        return qteProduit;
    }

    public void setQteProduit(int qteProduit) {
        this.qteProduit = qteProduit;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Date getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(Date datePublication) {
        this.datePublication = datePublication;
    }

    public String getStatutProduit() {
        return statutProduit;
    }

    public void setStatutProduit(String statutProduit) {
        this.statutProduit = statutProduit;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUniteMesure() {
        return uniteMesure;
    }

    public void setUniteMesure(String uniteMesure) {
        this.uniteMesure = uniteMesure;
    }

    public String getLocalite() {
        return localite;
    }

    public void setLocalite(String localite) {
        this.localite = localite;
    }

    public List<CommandeProduit> getCommandeProduitCollection() {
        return commandeProduitCollection;
    }

    public void setCommandeProduitCollection(List<CommandeProduit> commandeProduitCollection) {
        this.commandeProduitCollection = commandeProduitCollection;
    }

    public Categorie getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(Categorie idCategorie) {
        this.idCategorie = idCategorie;
    }

    public Utilisateur getIdAgriculteur() {
        return idAgriculteur;
    }

    public void setIdAgriculteur(Utilisateur idAgriculteur) {
        this.idAgriculteur = idAgriculteur;
    }

    public List<PrevisionRecolte> getPrevisionRecolteCollection() {
        return previsionRecolteCollection;
    }

    public void setPrevisionRecolteCollection(List<PrevisionRecolte> previsionRecolteCollection) {
        this.previsionRecolteCollection = previsionRecolteCollection;
    }

    public List<Commentaire> getCommentaireCollection() {
        return commentaireCollection;
    }

    public void setCommentaireCollection(List<Commentaire> commentaireCollection) {
        this.commentaireCollection = commentaireCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProduit != null ? idProduit.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produit)) {
            return false;
        }
        Produit other = (Produit) object;
        if ((this.idProduit == null && other.idProduit != null) || (this.idProduit != null && !this.idProduit.equals(other.idProduit))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.mboamarket.Produit[ idProduit=" + idProduit + " ]";
    }

}
