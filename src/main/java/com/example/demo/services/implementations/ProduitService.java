package com.example.demo.services.implementations;

import com.example.demo.dto.ProduitDTO;
import com.example.demo.enties.Categorie;
import com.example.demo.enties.Produit;
import com.example.demo.enties.Utilisateur;
import com.example.demo.enums.StatutProduit;
import com.example.demo.mappers.ProduitMapper;
import com.example.demo.repositories.CategorieRepos;
import com.example.demo.repositories.ProduitRepos;
import com.example.demo.repositories.UtilisateurRepos;
import com.example.demo.services.interfaces.ProduitInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service

public class ProduitService implements ProduitInterface {

    private final ProduitRepos produitRepos;
    private final CategorieRepos categorieRepos;
    private final UtilisateurRepos utilisateurRepos;
    private final ProduitMapper mapper;

    @Override
    public ProduitDTO save(ProduitDTO dto) {

        Produit produit = mapper.toEntity(dto);

        Categorie cat = categorieRepos.findById(dto.getIdCategorie())
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));

        Utilisateur agriculteur = utilisateurRepos.findById(dto.getIdAgriculteur())
                .orElseThrow(() -> new RuntimeException("Agriculteur non trouvé"));

        produit.setIdCategorie(cat);
        produit.setIdAgriculteur(agriculteur);
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
        return produitRepos.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public void delete(Integer id) {
        produitRepos.deleteById(id);
    }

    public ProduitService(ProduitRepos produitRepos, CategorieRepos categorieRepos, UtilisateurRepos utilisateurRepos, ProduitMapper mapper) {
        this.produitRepos = produitRepos;
        this.categorieRepos = categorieRepos;
        this.utilisateurRepos = utilisateurRepos;
        this.mapper = mapper;
    }
}