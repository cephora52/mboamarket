package com.example.demo.services.implementations;

import com.example.demo.dto.PaiementDTO;
import com.example.demo.enties.Commande;
import com.example.demo.enties.Paiement;
import com.example.demo.mappers.PaiementMapper;
import com.example.demo.repositories.CommandeRepos;
import com.example.demo.repositories.PaiementRepos;
import com.example.demo.services.interfaces.PaiementInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaiementService implements PaiementInterface {

    private final PaiementRepos paiementRepos;
    private final CommandeRepos commandeRepos;
    private final PaiementMapper mapper;

    public PaiementService(PaiementRepos paiementRepos,
                           CommandeRepos commandeRepos,
                           PaiementMapper mapper) {
        this.paiementRepos = paiementRepos;
        this.commandeRepos = commandeRepos;
        this.mapper = mapper;
    }

    @Override
    public PaiementDTO create(PaiementDTO dto) {

        Commande commande = commandeRepos.findById(dto.getIdCommande())
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        boolean paiementExiste = paiementRepos.findAll()
                .stream()
                .anyMatch(p -> p.getIdCommande() != null &&
                        p.getIdCommande().getIdCommande().equals(dto.getIdCommande()));

        if (paiementExiste) {
            throw new RuntimeException("Cette commande est déjà payée");
        }

        Paiement paiement = mapper.toEntity(dto);
        paiement.setIdCommande(commande);

        return mapper.toDTO(paiementRepos.save(paiement));
    }

    @Override
    public PaiementDTO update(Integer id, PaiementDTO dto) {

        Paiement paiement = paiementRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Paiement non trouvé"));

        paiement.setMontant(dto.getMontant());
        paiement.setDatePaiement(dto.getDatePaiement());

        return mapper.toDTO(paiementRepos.save(paiement));
    }

    @Override
    public PaiementDTO getById(Integer id) {
        return mapper.toDTO(
                paiementRepos.findById(id)
                        .orElseThrow(() -> new RuntimeException("Paiement non trouvé"))
        );
    }

    @Override
    public List<PaiementDTO> getAll() {
        return paiementRepos.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {

        Paiement paiement = paiementRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Paiement non trouvé"));

        paiementRepos.delete(paiement);
    }
}