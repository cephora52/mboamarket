package com.example.demo.controllers;

import com.example.demo.dto.CartItemDTO;
import com.example.demo.dto.PanierItemDTO;
import com.example.demo.enties.PanierItem;
import com.example.demo.enties.Produit;
import com.example.demo.enties.Utilisateur;
import com.example.demo.mappers.ProduitMapper;
import com.example.demo.repositories.PanierRepos;
import com.example.demo.repositories.ProduitRepos;
import com.example.demo.repositories.UtilisateurRepos;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/panier")
public class PanierController {

    private final PanierRepos panierRepos;
    private final ProduitRepos produitRepos;
    private final UtilisateurRepos utilisateurRepos;
    private final ProduitMapper produitMapper;

    public PanierController(PanierRepos panierRepos, ProduitRepos produitRepos, 
                             UtilisateurRepos utilisateurRepos, ProduitMapper produitMapper) {
        this.panierRepos = panierRepos;
        this.produitRepos = produitRepos;
        this.utilisateurRepos = utilisateurRepos;
        this.produitMapper = produitMapper;
    }

    @GetMapping("/{userId}")
    public List<PanierItemDTO> getCart(@PathVariable Integer userId) {
        return panierRepos.findByUtilisateurIdUtilisateur(userId)
                .stream()
                .map(item -> new PanierItemDTO(
                        item.getIdPanierItem(),
                        produitMapper.toDTO(item.getProduit()),
                        item.getQuantite()
                ))
                .collect(Collectors.toList());
    }

    @PostMapping("/{userId}")
    public void addToCart(@PathVariable Integer userId, @RequestBody CartItemDTO dto) {
        System.out.println("DEBUG: Ajout au panier pour l'utilisateur ID: " + userId + ", Produit ID: " + dto.getIdProduit());
        
        try {
            Optional<PanierItem> existing = panierRepos.findByUtilisateurIdUtilisateurAndProduitIdProduit(userId, dto.getIdProduit());

            if (existing.isPresent()) {
                PanierItem item = existing.get();
                item.setQuantite(dto.getQuantite()); 
                panierRepos.save(item);
            } else {
                Utilisateur user = utilisateurRepos.findById(userId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
                Produit produit = produitRepos.findById(dto.getIdProduit())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit non trouvé"));

                panierRepos.save(new PanierItem(user, produit, dto.getQuantite()));
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout au panier: " + e.getMessage());
            throw e;
        }
    }

    @DeleteMapping("/{userId}/{produitId}")
    @Transactional
    public void removeFromCart(@PathVariable Integer userId, @PathVariable Integer produitId) {
        panierRepos.deleteByUtilisateurIdUtilisateurAndProduitIdProduit(userId, produitId);
    }

    @DeleteMapping("/{userId}")
    @Transactional
    public void clearCart(@PathVariable Integer userId) {
        panierRepos.deleteByUtilisateurIdUtilisateur(userId);
    }
}
