package com.example.demo.controllers;

import com.example.demo.dto.ProduitDTO;
import com.example.demo.services.interfaces.ProduitInterface;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
@RequiredArgsConstructor
public class ProduitController {

    private final ProduitInterface produitService;

    @PostMapping
    public ProduitDTO creerProduit(@RequestBody ProduitDTO dto) {
        return produitService.save(dto);
    }

    @PutMapping("/{id}")
    public ProduitDTO modifierProduit(@PathVariable Integer id,
                                      @RequestBody ProduitDTO dto) {
        return produitService.update(id, dto);
    }

    @GetMapping("/{id}")
    public ProduitDTO getProduit(@PathVariable Integer id) {
        return produitService.findById(id);
    }

    @GetMapping
    public List<ProduitDTO> getAllProduits() {
        return produitService.findAll();
    }

    @DeleteMapping("/{id}")
    public void supprimerProduit(@PathVariable Integer id) {
        produitService.delete(id);

    }

    public ProduitController(ProduitInterface produitService) {
        this.produitService = produitService;
    }
}