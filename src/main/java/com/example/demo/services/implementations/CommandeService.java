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
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
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
    private final NotificationService notificationService;
    private final JdbcTemplate jdbcTemplate;

    public CommandeService(CommandeRepos commandeRepos,
                           UtilisateurRepos utilisateurRepos,
                           ProduitRepos produitRepos,
                           CommandeProduitRepos commandeProduitRepos,
                           PaiementRepos paiementRepos,
                           CommandeMapper mapper,
                           NotificationService notificationService,
                           JdbcTemplate jdbcTemplate) {
        this.commandeRepos = commandeRepos;
        this.utilisateurRepos = utilisateurRepos;
        this.produitRepos = produitRepos;
        this.commandeProduitRepos = commandeProduitRepos;
        this.paiementRepos = paiementRepos;
        this.mapper = mapper;
        this.notificationService = notificationService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void migrateEnumColumn() {
        try {
            jdbcTemplate.execute("ALTER TABLE commande MODIFY COLUMN statutCmd ENUM('ENCOURS','CONFIRMEE','ATTENTE_CONFIRMATION','LIVREE','ANNULEE')");
        } catch (Exception e) {
            System.err.println("ENUM migration skipped: " + e.getMessage());
        }
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
    @Transactional
    public CommandeDTO updateStatus(Integer id, String status) {
        Commande commande = commandeRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        StatutCommande newStatus;
        try {
            newStatus = StatutCommande.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Statut invalide: " + status);
        }

        StatutCommande current = commande.getStatutCmd();
        if (current == StatutCommande.ANNULEE || current == StatutCommande.LIVREE) {
            throw new RuntimeException("Impossible de changer le statut d'une commande " + current);
        }

        commande.setStatutCmd(newStatus);
        Commande saved = commandeRepos.save(commande);

        String titre = "Statut de commande mis à jour";
        String message = String.format("La commande #%d est maintenant : %s", id, newStatus);
        notificationService.create(commande.getIdDistributeur().getIdUtilisateur(), titre, message);
        notificationService.create(commande.getIdAgriculteur().getIdUtilisateur(), titre, message);

        return mapper.toDTO(saved);
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

            // Notify farmer about new order
            String titreAgri = "Nouvelle commande reçue";
            String messageAgri = String.format("Vous avez reçu une nouvelle commande d'un distributeur pour un total de %.2f FCFA.", total);
            notificationService.create(farmerId, titreAgri, messageAgri);
        }

        // Notify distributor about successful payment
        for (Map.Entry<Integer, List<CartItemDTO>> entry : itemsByFarmer.entrySet()) {
            Integer farmerId = entry.getKey();
            Utilisateur agriculteur = utilisateurRepos.findById(farmerId).get();
            List<CartItemDTO> farmerItems = entry.getValue();
            double total = farmerItems.stream()
                    .mapToDouble(item -> {
                        Produit p = produitRepos.findById(item.getIdProduit()).get();
                        return p.getPrix() * Math.min(item.getQuantite(), p.getQteProduit());
                    })
                    .sum();
            String titreDist = "Paiement réussi";
            String messageDist = String.format("Votre commande chez %s d'un montant de %.2f FCFA a été confirmée.", agriculteur.getNom(), total);
            notificationService.create(dto.getIdDistributeur(), titreDist, messageDist);
        }

        return createdCommandes.stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public CommandeDTO preparerLivraison(Integer id) {
        Commande commande = commandeRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        if (commande.getStatutCmd() != StatutCommande.CONFIRMEE) {
            throw new RuntimeException("Seules les commandes confirmées peuvent être préparées");
        }

        int quantiteLivree = 0;
        if (commande.getCommandeProduitCollection() != null) {
            quantiteLivree = commande.getCommandeProduitCollection().stream()
                    .mapToInt(CommandeProduit::getQuantite)
                    .sum();
        }

        commande.setQuantiteLivree(quantiteLivree);
        commande.setStatutCmd(StatutCommande.ENCOURS);
        Commande saved = commandeRepos.save(commande);

        safeNotify(commande.getIdDistributeur().getIdUtilisateur(),
                "Livraison prête",
                "La commande #" + id + " est prête ! Demandez la confirmation à l'agriculteur.");

        safeNotify(commande.getIdAgriculteur().getIdUtilisateur(),
                "Livraison préparée",
                "Vous avez préparé la commande #" + id + ". Quantité : " + quantiteLivree + ". Le distributeur va demander la confirmation.");

        return mapper.toDTO(saved);
    }

    @Override
    public CommandeDTO demanderConfirmation(Integer id) {
        Commande commande = commandeRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        if (commande.getStatutCmd() != StatutCommande.ENCOURS) {
            throw new RuntimeException("Statut incorrect (" + commande.getStatutCmd() + "). La commande doit être en cours de livraison.");
        }

        commande.setStatutCmd(StatutCommande.ATTENTE_CONFIRMATION);
        Commande saved = commandeRepos.save(commande);

        if (commande.getIdAgriculteur() != null) {
            safeNotify(commande.getIdAgriculteur().getIdUtilisateur(),
                    "Confirmation requise",
                    "Le distributeur demande la confirmation pour la commande #" + id);
        }
        if (commande.getIdDistributeur() != null) {
            safeNotify(commande.getIdDistributeur().getIdUtilisateur(),
                    "Demande envoyée",
                    "Votre demande de confirmation pour la commande #" + id + " a été envoyée à l'agriculteur.");
        }

        return mapper.toDTO(saved);
    }

    @Override
    public CommandeDTO validerParAgriculteur(Integer id) {
        Commande commande = commandeRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        if (commande.getStatutCmd() != StatutCommande.ATTENTE_CONFIRMATION) {
            throw new RuntimeException("Statut incorrect (" + commande.getStatutCmd() + "). La commande doit être en attente de confirmation.");
        }

        commande.setStatutCmd(StatutCommande.LIVREE);
        Commande saved = commandeRepos.save(commande);

        if (commande.getIdDistributeur() != null) {
            safeNotify(commande.getIdDistributeur().getIdUtilisateur(),
                    "Livraison confirmée",
                    "L'agriculteur a confirmé la livraison #" + id + " ! Montant : " + saved.getMontantTotal() + " FCFA");
        }
        if (commande.getIdAgriculteur() != null) {
            safeNotify(commande.getIdAgriculteur().getIdUtilisateur(),
                    "Livraison terminée",
                    "Vous avez confirmé la livraison #" + id + ". Montant : " + saved.getMontantTotal() + " FCFA");
        }

        return mapper.toDTO(saved);
    }

    private void safeNotify(Integer userId, String titre, String message) {
        try {
            notificationService.create(userId, titre, message);
        } catch (Exception e) {
            System.err.println("Notification failed for user " + userId + ": " + e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {

        Commande commande = commandeRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        commandeRepos.delete(commande);
    }
}