package com.example.demo.services.implementations;

import com.example.demo.dto.UtilisateurDTO;
import com.example.demo.enties.Utilisateur;
import com.example.demo.mappers.UtilisateurMapper;
import com.example.demo.repositories.UtilisateurRepos;
import com.example.demo.services.interfaces.UtilisateurInterface;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UtilisateurService implements UtilisateurInterface {

    private final UtilisateurRepos utilisateurRepos;
    private final UtilisateurMapper utilisateurMapper;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurService(UtilisateurRepos utilisateurRepos, 
                             UtilisateurMapper utilisateurMapper,
                             PasswordEncoder passwordEncoder) {
        this.utilisateurRepos = utilisateurRepos;
        this.utilisateurMapper = utilisateurMapper;
        this.passwordEncoder = passwordEncoder;
    }

    // ===== CREATE =====
    @Override
    public UtilisateurDTO save(UtilisateurDTO dto) {
        // Vérifier email unique
        if (utilisateurRepos.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }

        Utilisateur user = utilisateurMapper.toEntity(dto);
        return utilisateurMapper.toDTO(utilisateurRepos.save(user));
    }

    // ===== READ BY ID =====
    @Override
    public UtilisateurDTO findById(Integer id) {
        Utilisateur user = utilisateurRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return utilisateurMapper.toDTO(user);
    }

    // ===== READ ALL =====
    @Override
    public List<UtilisateurDTO> findAll() {
        return utilisateurRepos.findAll()
                .stream()
                .map(utilisateurMapper::toDTO)
                .toList();
    }

    @Override
    public UtilisateurDTO create(UtilisateurDTO dto) {
        return save(dto);
    }

    @Override
    public UtilisateurDTO getById(Integer id) {
        return findById(id);
    }

    @Override
    public List<UtilisateurDTO> getAll() {
        return findAll();
    }

    // ===== UPDATE =====
    @Override
    @Transactional
    public UtilisateurDTO update(Integer id, UtilisateurDTO dto) {
        try {
            Utilisateur user = utilisateurRepos.findById(id)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            // IMPORTANT: Capture existing password to prevent "null property" error
            String currentPassword = user.getPassword();

            if (dto.getNom() != null) user.setNom(dto.getNom());
            if (dto.getTelephone() != null) user.setTelephone(dto.getTelephone());
            if (dto.getVille() != null) user.setVille(dto.getVille());
            if (dto.getRole() != null) user.setRole(dto.getRole());
            if (dto.getEmail() != null) user.setEmail(dto.getEmail());
            
            // Password logic
            if (dto.getPassword() != null && !dto.getPassword().trim().isEmpty()) {
                user.setPassword(passwordEncoder.encode(dto.getPassword()));
            } else {
                user.setPassword(currentPassword); // Re-assert the existing password
            }

            if (dto.getBio() != null) user.setBio(dto.getBio());
            if (dto.getPhoto() != null) user.setPhoto(dto.getPhoto());

            return utilisateurMapper.toDTO(utilisateurRepos.save(user));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // ===== DELETE =====
    @Override
    public void delete(Integer id) {
        if (!utilisateurRepos.existsById(id)) {
            throw new RuntimeException("Utilisateur introuvable");
        }
        utilisateurRepos.deleteById(id);
    }
}