package com.example.demo.enties;

import com.example.demo.enums.StatutProduit;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "produit")
public class Produit implements Serializable {

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

    @Enumerated(EnumType.STRING)
    private StatutProduit statutProduit;

    private String photo;
    private String uniteMesure;
    private String localite;

    @ManyToOne
    @JoinColumn(name = "idCategorie")
    private Categorie idCategorie;

    @ManyToOne
    @JoinColumn(name = "idAgriculteur")
    private Utilisateur idAgriculteur;

    @OneToMany(mappedBy = "produit")
    private List<CommandeProduit> commandeProduitCollection;

    @OneToMany(mappedBy = "idProduit")
    private List<Commentaire> commentaireCollection;

    public Produit() {}

    public Integer getIdProduit() { return idProduit; }
    public void setIdProduit(Integer idProduit) { this.idProduit = idProduit; }

    public String getNomProduit() { return nomProduit; }
    public void setNomProduit(String nomProduit) { this.nomProduit = nomProduit; }

    public int getQteProduit() { return qteProduit; }
    public void setQteProduit(int qteProduit) { this.qteProduit = qteProduit; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    public Date getDatePublication() { return datePublication; }
    public void setDatePublication(Date datePublication) { this.datePublication = datePublication; }

    public StatutProduit getStatutProduit() { return statutProduit; }
    public void setStatutProduit(StatutProduit statutProduit) { this.statutProduit = statutProduit; }

    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }

    public String getUniteMesure() { return uniteMesure; }
    public void setUniteMesure(String uniteMesure) { this.uniteMesure = uniteMesure; }

    public String getLocalite() { return localite; }
    public void setLocalite(String localite) { this.localite = localite; }

    public Categorie getIdCategorie() { return idCategorie; }
    public void setIdCategorie(Categorie idCategorie) { this.idCategorie = idCategorie; }

    public Utilisateur getIdAgriculteur() { return idAgriculteur; }
    public void setIdAgriculteur(Utilisateur idAgriculteur) { this.idAgriculteur = idAgriculteur; }
}