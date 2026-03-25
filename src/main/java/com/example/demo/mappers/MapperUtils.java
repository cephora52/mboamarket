package com.example.demo.mappers;

import com.example.demo.enties.*;
import org.springframework.stereotype.Component;

@Component
public class MapperUtils {

    // ===== UTILISATEUR =====
    public Integer map(Utilisateur u) {
        return u == null ? null : u.getIdUtilisateur();
    }

    public Utilisateur mapUtilisateur(Integer id) {
        if (id == null) return null;
        Utilisateur u = new Utilisateur();
        u.setIdUtilisateur(id);
        return u;
    }

    // ===== PRODUIT =====
    public Integer map(Produit p) {
        return p == null ? null : p.getIdProduit();
    }

    public Produit mapProduit(Integer id) {
        if (id == null) return null;
        Produit p = new Produit();
        p.setIdProduit(id);
        return p;
    }

    // ===== COMMANDE =====
    public Integer map(Commande c) {
        return c == null ? null : c.getIdCommande();
    }

    public Commande mapCommande(Integer id) {
        if (id == null) return null;
        Commande c = new Commande();
        c.setIdCommande(id);
        return c;
    }

    // ===== PAIEMENT =====
    public Integer map(Paiement p) {
        return p == null ? null : p.getIdPaiement();
    }

    public Paiement mapPaiement(Integer id) {
        if (id == null) return null;
        Paiement p = new Paiement();
        p.setIdPaiement(id);
        return p;
    }

    // ===== CATEGORIE =====
    public Integer map(Categorie c) {
        return c == null ? null : c.getIdCategorie();
    }

    public Categorie mapCategorie(Integer id) {
        if (id == null) return null;
        Categorie c = new Categorie();
        c.setIdCategorie(id);
        return c;
    }

    // ===== CONVERSATION =====
    public Integer map(Conversation c) {
        return c == null ? null : c.getIdConversation();
    }

    public Conversation mapConversation(Integer id) {
        if (id == null) return null;
        Conversation c = new Conversation();
        c.setIdConversation(id);
        return c;
    }

    // ===== PREVISION =====
    public Integer map(PrevisionRecolte p) {
        return p == null ? null : p.getIdPrevision();
    }

    public PrevisionRecolte mapPrevision(Integer id) {
        if (id == null) return null;
        PrevisionRecolte p = new PrevisionRecolte();
        p.setIdPrevision(id);
        return p;
    }

    // ===== MESSAGE =====
    public Integer map(Message m) {
        return m == null ? null : m.getIdMessage();
    }

    public Message mapMessage(Integer id) {
        if (id == null) return null;
        Message m = new Message();
        m.setIdMessage(id);
        return m;
    }

}