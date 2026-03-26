package com.example.demo.services.implementations;

import com.example.demo.dto.CommandeDTO;
import com.example.demo.enties.Commande;
import com.example.demo.enties.Utilisateur;
import com.example.demo.mappers.CommandeMapper;
import com.example.demo.repositories.CommandeRepos;
import com.example.demo.repositories.UtilisateurRepos;
import com.example.demo.services.interfaces.CommandeInterface;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommandeService implements CommandeInterface {

    private final CommandeRepos commandeRepos;
    private final UtilisateurRepos utilisateurRepos;
    private final CommandeMapper mapper;

    public CommandeService(CommandeRepos commandeRepos,
                           UtilisateurRepos utilisateurRepos,
                           CommandeMapper mapper) {
        this.commandeRepos = commandeRepos;
        this.utilisateurRepos = utilisateurRepos;
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
    public void delete(Integer id) {

        Commande commande = commandeRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        commandeRepos.delete(commande);
    }
}