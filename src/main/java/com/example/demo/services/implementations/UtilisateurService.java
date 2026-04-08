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

public class UtilisateurService implements UtilisateurInterface {

    private final UtilisateurRepos utilisateurRepos;

    // ===== CREATE =====
    @Override
    public UtilisateurDTO save(UtilisateurDTO dto) {

        // Vérifier email unique
        if (utilisateurRepos.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }

        Utilisateur user = UtilisateurMapper.toEntity(dto);

        return UtilisateurMapper.toDTO(utilisateurRepos.save(user));
    }

    // ===== READ BY ID =====
    @Override
    public UtilisateurDTO findById(Integer id) {

        Utilisateur user = utilisateurRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        return UtilisateurMapper.toDTO(user);
    }

    // ===== READ ALL =====
    @Override
    public List<UtilisateurDTO> findAll() {

        return utilisateurRepos.findAll()
                .stream()
                .map(UtilisateurMapper::toDTO)
                .toList();
    }

    @Override
    public UtilisateurDTO create(UtilisateurDTO dto) {
        return null;
    }

    @Override
    public UtilisateurDTO getById(Integer id) {
        return null;
    }

    @Override
    public List<UtilisateurDTO> getAll() {
        return null;
    }

    // ===== UPDATE =====
    @Override
    public UtilisateurDTO update(Integer id, UtilisateurDTO dto) {

        Utilisateur user = utilisateurRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        user.setNom(dto.getNom());
        user.setTelephone(dto.getTelephone());
        user.setVille(dto.getVille());
        user.setRole(dto.getRole());

        // ⚠️ optionnel : update email/password
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        return UtilisateurMapper.toDTO(utilisateurRepos.save(user));
    }

    // ===== DELETE =====
    @Override
    public void delete(Integer id) {

        if (!utilisateurRepos.existsById(id)) {
            throw new RuntimeException("Utilisateur introuvable");
        }

        utilisateurRepos.deleteById(id);
    }

    public UtilisateurService(UtilisateurRepos utilisateurRepos) {
        this.utilisateurRepos = utilisateurRepos;
    }
}