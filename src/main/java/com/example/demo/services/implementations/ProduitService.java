package com.example.demo.services.implementations;

import com.example.demo.dto.ProduitDTO;
import com.example.demo.enties.Categorie;
import com.example.demo.enties.Produit;
import com.example.demo.enties.Utilisateur;
import com.example.demo.mappers.ProduitMapper;
import com.example.demo.repositories.CategorieRepos;
import com.example.demo.repositories.ProduitRepos;
import com.example.demo.repositories.UtilisateurRepos;
import com.example.demo.services.interfaces.ProduitInterface;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProduitService implements ProduitInterface {

    private final ProduitRepos produitRepos;
    private final CategorieRepos categorieRepos;
    private final UtilisateurRepos utilisateurRepos;
    private final ProduitMapper produitMapper;

    public ProduitService(ProduitRepos produitRepos, CategorieRepos categorieRepos, UtilisateurRepos utilisateurRepos, ProduitMapper produitMapper) {
        this.produitRepos = produitRepos;
        this.categorieRepos = categorieRepos;
        this.utilisateurRepos = utilisateurRepos;
        this.produitMapper = produitMapper;
    }

    @Override
    public ProduitDTO save(ProduitDTO dto) {

        Produit produit = produitMapper.toEntity(dto);

        Categorie categorie = categorieRepos.findById(dto.getIdCategorie())
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));

        Utilisateur agriculteur = utilisateurRepos.findById(dto.getIdAgriculteur())
                .orElseThrow(() -> new RuntimeException("Utilisateur (agriculteur) non trouvé"));

        produit.setIdCategorie(categorie);
        produit.setIdAgriculteur(agriculteur);

        return produitMapper.toDTO(produitRepos.save(produit));
    }

    @Override
    public ProduitDTO update(Integer id, ProduitDTO dto) {

        Produit produit = produitRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        produit.setNomProduit(dto.getNomProduit());
        produit.setPrix(dto.getPrix());
        produit.setQteProduit(dto.getQteProduit());
        produit.setLocalite(dto.getLocalite());
        produit.setStatutProduit(dto.getStatutProduit());
        produit.setPhoto(dto.getPhoto());
        produit.setUniteMesure(dto.getUniteMesure());

        return produitMapper.toDTO(produitRepos.save(produit));
    }

    @Override
    public ProduitDTO findById(Integer id) {

        Produit produit = produitRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        return produitMapper.toDTO(produit);
    }

    @Override
    public List<ProduitDTO> findAll() {

        return produitRepos.findAll()
                .stream()
                .map(produitMapper::toDTO)
                .toList();
    }

    @Override
    public void delete(Integer id) {

        if (!produitRepos.existsById(id)) {
            throw new RuntimeException("Produit introuvable pour suppression");
        }

        produitRepos.deleteById(id);
    }
}