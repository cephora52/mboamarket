package com.example.demo.enties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.demo.enums.Role;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "utilisateur")
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;

    // ===== ID =====
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUtilisateur;

    // ===== INFOS =====
    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String telephone;

    private String ville;

    // ===== AUTH =====
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // ===== ROLE =====
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "dateCreation", updatable = false)
    private LocalDateTime dateCreation;

    @Column(columnDefinition = "LONGTEXT")
    private String bio;

    @Column(columnDefinition = "LONGTEXT")
    private String photo;

    @PrePersist
    protected void onCreate() {
        if (this.dateCreation == null) {
            this.dateCreation = LocalDateTime.now();
        }
    }

    // ===== RELATIONS =====


    @JsonIgnore
    @OneToMany(mappedBy = "idUtilisateur", cascade = CascadeType.ALL)
    private List<Notification> notificationCollection;

    @JsonIgnore
    @OneToMany(mappedBy = "idAgriculteur", cascade = CascadeType.ALL)
    private List<Produit> produitCollection;

    @OneToOne(mappedBy = "idAgriculteur")
    private IndiceFiabilite indiceFiabilite;

    @JsonIgnore
    @OneToMany(mappedBy = "idDistributeur", cascade = CascadeType.ALL)
    private List<Commande> commandeCollection;

    @JsonIgnore
    @OneToMany(mappedBy = "idDistributeur", cascade = CascadeType.ALL)
    private List<Commentaire> commentaireCollection;

    // ===== CONSTRUCTEURS =====
    public Utilisateur() {}

    public Utilisateur(Integer idUtilisateur, String nom, String telephone, String email, String password, Role role) {
        this.idUtilisateur = idUtilisateur;
        this.nom = nom;
        this.telephone = telephone;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // ===== GETTERS & SETTERS =====

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }



    public List<Notification> getNotificationCollection() {
        return notificationCollection;
    }

    public void setNotificationCollection(List<Notification> notificationCollection) {
        this.notificationCollection = notificationCollection;
    }

    public List<Produit> getProduitCollection() {
        return produitCollection;
    }

    public void setProduitCollection(List<Produit> produitCollection) {
        this.produitCollection = produitCollection;
    }

    public IndiceFiabilite getIndiceFiabilite() {
        return indiceFiabilite;
    }

    public void setIndiceFiabilite(IndiceFiabilite indiceFiabilite) {
        this.indiceFiabilite = indiceFiabilite;
    }

    public List<Commande> getCommandeCollection() {
        return commandeCollection;
    }

    public void setCommandeCollection(List<Commande> commandeCollection) {
        this.commandeCollection = commandeCollection;
    }

    public List<Commentaire> getCommentaireCollection() {
        return commentaireCollection;
    }

    public void setCommentaireCollection(List<Commentaire> commentaireCollection) {
        this.commentaireCollection = commentaireCollection;
    }

    // ===== EQUALS & HASHCODE =====
    @Override
    public int hashCode() {
        return (idUtilisateur != null ? idUtilisateur.hashCode() : 0);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Utilisateur)) {
            return false;
        }
        Utilisateur other = (Utilisateur) object;
        return (this.idUtilisateur != null && this.idUtilisateur.equals(other.idUtilisateur));
    }

    @Override
    public String toString() {
        return "Utilisateur[id=" + idUtilisateur + "]";
    }
}