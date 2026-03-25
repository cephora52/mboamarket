package com.example.demo.controllers;

import com.example.demo.dto.CommandeProduitDTO;
import com.example.demo.services.interfaces.CommandeProduitInterface;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commande-produits")
@RequiredArgsConstructor
public class CommandeProduitController {

    private final CommandeProduitInterface service;

    @PostMapping
    public CommandeProduitDTO ajouterProduit(@RequestBody CommandeProduitDTO dto) {
        return service.ajouterProduitDansCommande(dto);
    }

    @GetMapping("/{idCommande}")
    public List<CommandeProduitDTO> produitsCommande(@PathVariable Integer idCommande) {
        return service.getProduitsCommande(idCommande);
    }

    @DeleteMapping("/{idCommande}/{idProduit}")
    public void supprimerProduit(@PathVariable Integer idCommande,
                                 @PathVariable Integer idProduit) {
        service.supprimerProduitCommande(idCommande, idProduit);
    }

    public CommandeProduitController(CommandeProduitInterface service) {
        this.service = service;
    }
}