package com.example.demo.enties;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "produit")
public class Produit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProduit;

    @Column(nullable = false)
    private String nomProduit;

    @Column(nullable = false)
    private int qteProduit;

    @Column(nullable = false)
    private double prix;

    @Temporal(TemporalType.TIMESTAMP)
    private Date datePublication;

    private String statutProduit;

    private String photo;

    private String uniteMesure;

    private String localite;

    // ===== RELATIONS =====

    @ManyToOne
    @JoinColumn(name = "idCategorie")
    private Categorie idCategorie;

    @ManyToOne
    @JoinColumn(name = "idAgriculteur")
    private Utilisateur idAgriculteur;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private List<CommandeProduit> commandeProduitCollection;

    @OneToMany(mappedBy = "idProduit", cascade = CascadeType.ALL)
    private List<Commentaire> commentaireCollection;

    public Produit() {
    }

    // ===== GETTERS SETTERS =====

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

    public List<CommandeProduit> getCommandeProduitCollection() {
        return commandeProduitCollection;
    }

    public void setCommandeProduitCollection(List<CommandeProduit> commandeProduitCollection) {
        this.commandeProduitCollection = commandeProduitCollection;
    }

    public List<Commentaire> getCommentaireCollection() {
        return commentaireCollection;
    }

    public void setCommentaireCollection(List<Commentaire> commentaireCollection) {
        this.commentaireCollection = commentaireCollection;
    }
}