package com.example.demo.services.implementations;

import com.example.demo.dto.ProduitDTO;
import com.example.demo.enties.Categorie;
import com.example.demo.enties.Produit;
import com.example.demo.enties.Utilisateur;
import com.example.demo.enties.Commande;
import com.example.demo.enums.Role;
import com.example.demo.enums.StatutProduit;
import com.example.demo.mappers.ProduitMapper;
import com.example.demo.repositories.CategorieRepos;
import com.example.demo.repositories.ProduitRepos;
import com.example.demo.repositories.UtilisateurRepos;
import com.example.demo.repositories.CommandeRepos;
import com.example.demo.services.interfaces.ProduitInterface;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProduitService implements ProduitInterface {

    private final ProduitRepos produitRepos;
    private final CategorieRepos categorieRepos;
    private final UtilisateurRepos utilisateurRepos;
    private final CommandeRepos commandeRepos;
    private final ProduitMapper mapper;

    @Override
    public ProduitDTO save(ProduitDTO dto) {

        Produit produit = mapper.toEntity(dto);

        Categorie cat = categorieRepos.findById(dto.getIdCategorie())
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));

        if (dto.getIdAgriculteur() != null) {
            Utilisateur agriculteur = utilisateurRepos.findById(dto.getIdAgriculteur()).orElse(null);
            produit.setIdAgriculteur(agriculteur);
        } else {
            produit.setIdAgriculteur(null);
        }
        produit.setIdCategorie(cat);
        produit.setDatePublication(new Date());

        // logique automatique statut
        if (produit.getQteProduit() == 0)
            produit.setStatutProduit(StatutProduit.EPUISE);
        else
            produit.setStatutProduit(StatutProduit.DISPONIBLE);

        return mapper.toDTO(produitRepos.save(produit));
    }

    @Override
    public ProduitDTO update(Integer id, ProduitDTO dto) {

        Produit produit = produitRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        produit.setNomProduit(dto.getNomProduit());
        produit.setPrix(dto.getPrix());
        produit.setQteProduit(dto.getQteProduit());

        if (dto.getQteProduit() == 0)
            produit.setStatutProduit(StatutProduit.EPUISE);
        else
            produit.setStatutProduit(StatutProduit.DISPONIBLE);

        return mapper.toDTO(produitRepos.save(produit));
    }

    @Override
    public ProduitDTO findById(Integer id) {
        return mapper.toDTO(
                produitRepos.findById(id)
                        .orElseThrow(() -> new RuntimeException("Produit non trouvé"))
        );
    }

    @Override
    public List<ProduitDTO> findAll() {
        List<Produit> results = produitRepos.findAll();
        System.out.println("MboaMarket: findAll() - Produits trouvés en base: " + results.size());
        return results.stream().map(mapper::toDTO).toList();
    }

    @Override
    public List<ProduitDTO> findByAgriculteur(Integer id) {
        System.out.println("MboaMarket: Recherche de produits pour idUtilisateur=" + id);
        List<Produit> results = produitRepos.findByIdAgriculteurIdUtilisateur(id);
        System.out.println("MboaMarket: Produits trouvés en base: " + results.size());
        
        List<ProduitDTO> dtos = results.stream().map(p -> {
            try {
                return mapper.toDTO(p);
            } catch (Exception e) {
                System.err.println("MboaMarket: Erreur de mapping pour le produit " + p.getIdProduit());
                return null;
            }
        }).filter(d -> d != null).toList();
        
        System.out.println("MboaMarket: DTOs renvoyés au frontend: " + dtos.size());
        return dtos;
    }

    @Override
    public void delete(Integer id) {
        produitRepos.deleteById(id);
    }

    public ProduitService(ProduitRepos produitRepos, CategorieRepos categorieRepos, UtilisateurRepos utilisateurRepos, CommandeRepos commandeRepos, ProduitMapper mapper) {
        this.produitRepos = produitRepos;
        this.categorieRepos = categorieRepos;
        this.utilisateurRepos = utilisateurRepos;
        this.commandeRepos = commandeRepos;
        this.mapper = mapper;
    }
}