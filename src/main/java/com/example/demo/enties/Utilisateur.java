package com.example.demo.enties;


import jakarta.persistence.*;
import java.util.List;
import java.io.Serializable;

@Entity
@Table(name = "utilisateur")
@NamedQueries({
        @NamedQuery(name = "Utilisateur.findAll", query = "SELECT u FROM Utilisateur u"),
        @NamedQuery(name = "Utilisateur.findByIdUtilisateur", query = "SELECT u FROM Utilisateur u WHERE u.idUtilisateur = :idUtilisateur"),
        @NamedQuery(name = "Utilisateur.findByNom", query = "SELECT u FROM Utilisateur u WHERE u.nom = :nom"),
        @NamedQuery(name = "Utilisateur.findByTelephone", query = "SELECT u FROM Utilisateur u WHERE u.telephone = :telephone"),
        @NamedQuery(name = "Utilisateur.findByVille", query = "SELECT u FROM Utilisateur u WHERE u.ville = :ville"),
        @NamedQuery(name = "Utilisateur.findByRole", query = "SELECT u FROM Utilisateur u WHERE u.role = :role")})
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idUtilisateur")
    private Integer idUtilisateur;
    @Basic(optional = false)
    @Column(name = "nom")
    private String nom;
    @Basic(optional = false)
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "ville")
    private String ville;
    @Basic(optional = false)
    @Column(name = "role")
    private String role;
    @JoinTable(name = "utilisateur_conversation", joinColumns = {
            @JoinColumn(name = "idUtilisateur", referencedColumnName = "idUtilisateur")}, inverseJoinColumns = {
            @JoinColumn(name = "idConversation", referencedColumnName = "idConversation")})
    @ManyToMany
    private List<Conversation> conversationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUtilisateur")
    private List<Notification> notificationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAgriculteur")
    private List<Produit> produitCollection;
    @OneToOne(mappedBy = "idAgriculteur")
    private IndiceFiabilite indiceFiabilite;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDistributeur")
    private List<Commande> commandeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDistributeur")
    private List<Commentaire> commentaireCollection;

    public Utilisateur() {
    }

    public Utilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Utilisateur(Integer idUtilisateur, String nom, String telephone, String role) {
        this.idUtilisateur = idUtilisateur;
        this.nom = nom;
        this.telephone = telephone;
        this.role = role;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Conversation> getConversationCollection() {
        return conversationCollection;
    }

    public void setConversationCollection(List<Conversation> conversationCollection) {
        this.conversationCollection = conversationCollection;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUtilisateur != null ? idUtilisateur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Utilisateur)) {
            return false;
        }
        Utilisateur other = (Utilisateur) object;
        if ((this.idUtilisateur == null && other.idUtilisateur != null) || (this.idUtilisateur != null && !this.idUtilisateur.equals(other.idUtilisateur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.mboamarket.Utilisateur[ idUtilisateur=" + idUtilisateur + " ]";
    }

}
