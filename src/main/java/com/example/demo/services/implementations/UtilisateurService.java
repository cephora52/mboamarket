package com.example.demo.services.implementations;

import com.example.demo.dto.UtilisateurDTO;
import com.example.demo.enties.Utilisateur;
import com.example.demo.mappers.UtilisateurMapper;
import com.example.demo.repositories.UtilisateurRepos;
import com.example.demo.services.interfaces.UtilisateurInterface;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UtilisateurService implements UtilisateurInterface {

    private final UtilisateurRepos utilisateurRepos;
    private final UtilisateurMapper mapper;

    public UtilisateurService(UtilisateurRepos utilisateurRepos, UtilisateurMapper mapper) {
        this.utilisateurRepos = utilisateurRepos;
        this.mapper = mapper;
    }

    @Override
    public UtilisateurDTO create(UtilisateurDTO dto) {

        Utilisateur user = mapper.toEntity(dto);

        return mapper.toDTO(utilisateurRepos.save(user));
    }

    @Override
    public UtilisateurDTO getById(Integer id) {

        Utilisateur user = utilisateurRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        return mapper.toDTO(user);
    }

    @Override
    public List<UtilisateurDTO> getAll() {

        return utilisateurRepos.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public UtilisateurDTO update(Integer id, UtilisateurDTO dto) {

        Utilisateur user = utilisateurRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        user.setNom(dto.getNom());
        user.setTelephone(dto.getTelephone());
        user.setVille(dto.getVille());
        user.setRole(dto.getRole());

        return mapper.toDTO(utilisateurRepos.save(user));
    }

    @Override
    public void delete(Integer id) {

        Utilisateur user = utilisateurRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        utilisateurRepos.delete(user);
    }
}