package com.example.demo.controllers;

import com.example.demo.dto.FavorisDTO;
import com.example.demo.dto.ProduitDTO;
import com.example.demo.enties.Favoris;
import com.example.demo.enties.Produit;
import com.example.demo.enties.Utilisateur;
import com.example.demo.mappers.ProduitMapper;
import com.example.demo.repositories.FavorisRepos;
import com.example.demo.repositories.ProduitRepos;
import com.example.demo.repositories.UtilisateurRepos;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/favoris")
public class FavorisController {

    private final FavorisRepos favorisRepos;
    private final ProduitRepos produitRepos;
    private final UtilisateurRepos utilisateurRepos;
    private final ProduitMapper produitMapper;

    public FavorisController(FavorisRepos favorisRepos, ProduitRepos produitRepos, 
                             UtilisateurRepos utilisateurRepos, ProduitMapper produitMapper) {
        this.favorisRepos = favorisRepos;
        this.produitRepos = produitRepos;
        this.utilisateurRepos = utilisateurRepos;
        this.produitMapper = produitMapper;
    }

    @GetMapping("/{userId}")
    public List<ProduitDTO> getFavorites(@PathVariable Integer userId) {
        return favorisRepos.findByUtilisateurIdUtilisateur(userId)
                .stream()
                .map(f -> produitMapper.toDTO(f.getProduit()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public void addFavorite(@RequestBody FavorisDTO dto) {
        System.out.println("DEBUG: Ajout aux favoris - User ID: " + dto.getIdUtilisateur() + ", Produit ID: " + dto.getIdProduit());
        
        try {
            if (favorisRepos.findByUtilisateurIdUtilisateurAndProduitIdProduit(dto.getIdUtilisateur(), dto.getIdProduit()).isPresent()) {
                System.out.println("INFO: Produit déjà en favoris.");
                return;
            }

            Utilisateur user = utilisateurRepos.findById(dto.getIdUtilisateur())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
            
            Produit produit = produitRepos.findById(dto.getIdProduit())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit non trouvé"));

            favorisRepos.save(new Favoris(user, produit));
            System.out.println("SUCCESS: Favoris enregistré.");
        } catch (Exception e) {
            System.err.println("CRITICAL: Erreur favoris - " + e.getMessage());
            throw e;
        }
    }

    @DeleteMapping("/{userId}/{produitId}")
    @Transactional
    public void removeFavorite(@PathVariable Integer userId, @PathVariable Integer produitId) {
        favorisRepos.deleteByUtilisateurIdUtilisateurAndProduitIdProduit(userId, produitId);
    }
}
