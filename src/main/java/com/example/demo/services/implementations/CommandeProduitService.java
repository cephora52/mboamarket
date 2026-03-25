package com.example.demo.services.implementations;

import com.example.demo.dto.CommandeProduitDTO;
import com.example.demo.enties.*;
import com.example.demo.mappers.CommandeProduitMapper;
import com.example.demo.repositories.*;

import com.example.demo.services.interfaces.CommandeProduitInterface;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandeProduitService implements CommandeProduitInterface {

    private final CommandeProduitRepos commandeProduitRepos;
    private final CommandeRepos commandeRepos;
    private final ProduitRepos produitRepos;
    private final CommandeProduitMapper mapper;

    public CommandeProduitService(CommandeProduitRepos commandeProduitRepos, CommandeRepos commandeRepos, ProduitRepos produitRepos, CommandeProduitMapper mapper) {
        this.commandeProduitRepos = commandeProduitRepos;
        this.commandeRepos = commandeRepos;
        this.produitRepos = produitRepos;
        this.mapper = mapper;
    }

    @Override
    public CommandeProduitDTO ajouterProduitDansCommande(CommandeProduitDTO dto) {

        Commande commande = commandeRepos.findById(dto.getIdCommande())
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        Produit produit = produitRepos.findById(dto.getIdProduit())
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        if (produit.getQteProduit() < dto.getQuantite()) {
            throw new RuntimeException("Stock insuffisant");
        }

        CommandeProduitPK pk = new CommandeProduitPK(dto.getIdCommande(), dto.getIdProduit());

        CommandeProduit cp = new CommandeProduit();
        cp.setCommandeProduitPK(pk);
        cp.setCommande(commande);
        cp.setProduit(produit);
        cp.setQuantite(dto.getQuantite());

        // 🔥 Mise à jour stock produit
        produit.setQteProduit(produit.getQteProduit() - dto.getQuantite());

        // 🔥 Mise à jour montant commande
        double montantAjout = produit.getPrix() * dto.getQuantite();
        commande.setMontantTotal(commande.getMontantTotal() + montantAjout);

        commandeProduitRepos.save(cp);
        produitRepos.save(produit);
        commandeRepos.save(commande);

        return mapper.toDTO(cp);
    }

    @Override
    public List<CommandeProduitDTO> getProduitsCommande(Integer idCommande) {

        return commandeProduitRepos.findAll()
                .stream()
                .filter(cp -> cp.getCommandeProduitPK().getIdCommande() == idCommande)
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public void supprimerProduitCommande(Integer idCommande, Integer idProduit) {

        CommandeProduitPK pk = new CommandeProduitPK(idCommande, idProduit);

        CommandeProduit cp = commandeProduitRepos.findById(pk)
                .orElseThrow(() -> new RuntimeException("Produit non présent dans la commande"));

        Produit produit = cp.getProduit();
        Commande commande = cp.getCommande();

        //  remettre stock
        produit.setQteProduit(produit.getQteProduit() + cp.getQuantite());

        //  diminuer montant commande
        double montant = produit.getPrix() * cp.getQuantite();
        commande.setMontantTotal(commande.getMontantTotal() - montant);

        commandeProduitRepos.delete(cp);

        produitRepos.save(produit);
        commandeRepos.save(commande);
    }
}