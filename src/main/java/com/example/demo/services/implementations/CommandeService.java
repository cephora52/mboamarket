package com.example.demo.services.implementations;

import com.example.demo.dto.CommandeDTO;
import com.example.demo.enties.Commande;
import com.example.demo.enties.Utilisateur;
import com.example.demo.mappers.CommandeMapper;
import com.example.demo.repositories.CommandeRepos;
import com.example.demo.repositories.UtilisateurRepos;
import com.example.demo.services.interfaces.CommandeInterface;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandeService implements CommandeInterface {

    private final CommandeRepos commandeRepos;
    private final UtilisateurRepos utilisateurRepos;
    private final CommandeMapper commandeMapper;

    public CommandeService(CommandeRepos commandeRepos, UtilisateurRepos utilisateurRepos, CommandeMapper commandeMapper) {
        this.commandeRepos = commandeRepos;
        this.utilisateurRepos = utilisateurRepos;
        this.commandeMapper = commandeMapper;
    }

    @Override
    public CommandeDTO save(CommandeDTO dto) {

        Commande commande = commandeMapper.toEntity(dto);

        Utilisateur distributeur = utilisateurRepos.findById(dto.getIdDistributeur())
                .orElseThrow(() -> new RuntimeException("Distributeur non trouvé"));

        commande.setIdDistributeur(distributeur);

        commande.setDateCommande(new Date());
        commande.setStatutCmd("EN_ATTENTE");

        return commandeMapper.toDTO(commandeRepos.save(commande));
    }

    @Override
    public CommandeDTO update(Integer id, CommandeDTO dto) {

        Commande commande = commandeRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        commande.setQteCommande(dto.getQteCommande());
        commande.setMontantTotal(dto.getMontantTotal());
        commande.setStatutCmd(dto.getStatutCmd());

        return commandeMapper.toDTO(commandeRepos.save(commande));
    }

    @Override
    public CommandeDTO findById(Integer id) {

        Commande commande = commandeRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        return commandeMapper.toDTO(commande);
    }

    @Override
    public List<CommandeDTO> findAll() {

        return commandeRepos.findAll()
                .stream()
                .map(commandeMapper::toDTO)
                .toList();
    }

    @Override
    public void delete(Integer id) {

        if (!commandeRepos.existsById(id)) {
            throw new RuntimeException("Commande introuvable");
        }

        commandeRepos.deleteById(id);
    }

    @Override
    public List<CommandeDTO> getAll() {
        return null;
    }

    @Override
    public CommandeDTO getById(Integer id) {
        return null;
    }

    @Override
    public CommandeDTO create(CommandeDTO dto) {
        return null;
    }
}