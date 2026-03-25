package com.example.demo.dto;
import java.util.Date;

public class CommentaireDTO {

    private Integer idCommentaire;
    private String contenu;
    private Date dateCommentaire;

    // relations simplifiées
    private Integer idProduit;
    private Integer idUtilisateur;

    // Getters & Setters
    public Integer getIdCommentaire() {
        return idCommentaire;
    }

    public void setIdCommentaire(Integer idCommentaire) {
        this.idCommentaire = idCommentaire;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDateCommentaire() {
        return dateCommentaire;
    }

    public void setDateCommentaire(Date dateCommentaire) {
        this.dateCommentaire = dateCommentaire;
    }

    public Integer getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Integer idProduit) {
        this.idProduit = idProduit;
    }

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public CommentaireDTO(Integer idCommentaire, String contenu, Date dateCommentaire, Integer idProduit, Integer idUtilisateur) {
        this.idCommentaire = idCommentaire;
        this.contenu = contenu;
        this.dateCommentaire = dateCommentaire;
        this.idProduit = idProduit;
        this.idUtilisateur = idUtilisateur;
    }

    public CommentaireDTO() {

    }

    public void setIdDistributeur(Integer idUtilisateur) {
    }

    public Integer getIdDistributeur() {
        return null;
    }
}
