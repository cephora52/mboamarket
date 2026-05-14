package com.example.demo.services.implementations;

import com.example.demo.dto.CartItemDTO;
import com.example.demo.dto.CheckoutRequestDTO;
import com.example.demo.dto.CommandeDTO;
import com.example.demo.enties.*;
import com.example.demo.enums.StatutCommande;
import com.example.demo.enums.StatutProduit;
import com.example.demo.mappers.CommandeMapper;
import com.example.demo.repositories.*;
import com.example.demo.services.interfaces.CommandeInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommandeService implements CommandeInterface {

    private final CommandeRepos commandeRepos;
    private final UtilisateurRepos utilisateurRepos;
    private final ProduitRepos produitRepos;
    private final CommandeProduitRepos commandeProduitRepos;
    private final PaiementRepos paiementRepos;
    private final CommandeMapper mapper;

    public CommandeService(CommandeRepos commandeRepos,
                           UtilisateurRepos utilisateurRepos,
                           ProduitRepos produitRepos,
                           CommandeProduitRepos commandeProduitRepos,
                           PaiementRepos paiementRepos,
                           CommandeMapper mapper) {
        this.commandeRepos = commandeRepos;
        this.utilisateurRepos = utilisateurRepos;
        this.produitRepos = produitRepos;
        this.commandeProduitRepos = commandeProduitRepos;
        this.paiementRepos = paiementRepos;
        this.mapper = mapper;
    }

    @Override
    public CommandeDTO create(CommandeDTO dto) {

        Utilisateur distributeur = utilisateurRepos.findById(dto.getIdDistributeur())
                .orElseThrow(() -> new RuntimeException("Distributeur non trouvé"));

        Commande commande = mapper.toEntity(dto);

        commande.setDateCommande(new Date());

        commande.setIdDistributeur(distributeur);

        return mapper.toDTO(commandeRepos.save(commande));
    }

    @Override
    public CommandeDTO update(Integer id, CommandeDTO dto) {

        Commande commande = commandeRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        commande.setMontantTotal(dto.getMontantTotal());
        commande.setStatutCmd(dto.getStatutCmd());

        return mapper.toDTO(commandeRepos.save(commande));
    }

    @Override
    public CommandeDTO getById(Integer id) {

        return mapper.toDTO(
                commandeRepos.findById(id)
                        .orElseThrow(() -> new RuntimeException("Commande non trouvée"))
        );
    }

    @Override
    public List<CommandeDTO> getAll() {

        return commandeRepos.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommandeDTO> getByDistributeur(Integer id) {
        return commandeRepos.findByIdDistributeurIdUtilisateur(id)
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<CommandeDTO> getByAgriculteur(Integer id) {
        return commandeRepos.findByIdAgriculteurIdUtilisateur(id)
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<CommandeDTO> payerPanier(CheckoutRequestDTO dto) {
        Utilisateur distributeur = utilisateurRepos.findById(dto.getIdDistributeur())
                .orElseThrow(() -> new RuntimeException("Distributeur non trouvé"));

        List<Commande> createdCommandes = new ArrayList<>();

        // 1. Group items by farmer
        Map<Integer, List<CartItemDTO>> itemsByFarmer = new HashMap<>();
        for (CartItemDTO item : dto.getItems()) {
            Produit p = produitRepos.findById(item.getIdProduit())
                    .orElseThrow(() -> new RuntimeException("Produit non trouvé ID: " + item.getIdProduit()));
            
            // "Ne pas inclure les produits en rupture de stock"
            if (p.getQteProduit() <= 0) continue;

            Integer farmerId = p.getIdAgriculteur().getIdUtilisateur();
            itemsByFarmer.computeIfAbsent(farmerId, k -> new ArrayList<>()).add(item);
        }

        // 2. Create one order per farmer
        for (Map.Entry<Integer, List<CartItemDTO>> entry : itemsByFarmer.entrySet()) {
            Integer farmerId = entry.getKey();
            List<CartItemDTO> farmerItems = entry.getValue();
            Utilisateur agriculteur = utilisateurRepos.findById(farmerId).get();

            Commande commande = new Commande();
            commande.setIdDistributeur(distributeur);
            commande.setIdAgriculteur(agriculteur);
            commande.setDateCommande(new Date());
            commande.setStatutCmd(StatutCommande.CONFIRMEE); // On suppose que le paiement est fait

            double total = 0;
            List<CommandeProduit> commandeProduits = new ArrayList<>();

            for (CartItemDTO itemDTO : farmerItems) {
                Produit p = produitRepos.findById(itemDTO.getIdProduit()).get();
                
                int qty = Math.min(itemDTO.getQuantite(), p.getQteProduit());
                total += p.getPrix() * qty;

                // Update Stock
                p.setQteProduit(p.getQteProduit() - qty);
                if (p.getQteProduit() == 0) p.setStatutProduit(StatutProduit.EPUISE);
                produitRepos.save(p);

                // Entry for join table
                CommandeProduit cp = new CommandeProduit();
                cp.setCommande(commande);
                cp.setProduit(p);
                cp.setQuantite(qty);
                // PK is set after commande is saved or manually
                commandeProduits.add(cp);
            }

            commande.setMontantTotal(total);
            Commande savedCommande = commandeRepos.save(commande);

            // Save relationship entries
            for (CommandeProduit cp : commandeProduits) {
                cp.setCommandeProduitPK(new CommandeProduitPK(savedCommande.getIdCommande(), cp.getProduit().getIdProduit()));
                commandeProduitRepos.save(cp);
            }

            // Create Payment record
            Paiement paiement = new Paiement();
            paiement.setMontant(total);
            paiement.setDatePaiement(new Date());
            paiement.setIdCommande(savedCommande);
            paiementRepos.save(paiement);

            createdCommandes.add(savedCommande);
        }

        return createdCommandes.stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {

        Commande commande = commandeRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        commandeRepos.delete(commande);
    }
}