package com.example.demo.services.implementations;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.UtilisateurDTO;
import com.example.demo.enties.Utilisateur;
import com.example.demo.repositories.UtilisateurRepos;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class AuthService {

    private final UtilisateurRepos utilisateurRepos;
    private final PasswordEncoder passwordEncoder;

    // ===== REGISTER =====
    public AuthResponse register(UtilisateurDTO dto) {

        if (utilisateurRepos.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }

        // ✅ CRÉATION MANUELLE (CORRIGE LE NULL)
        Utilisateur user = new Utilisateur();

        user.setNom(dto.getNom());
        user.setTelephone(dto.getTelephone());
        user.setVille(dto.getVille());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());

        // 🔐 PASSWORD HASH
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        utilisateurRepos.save(user);

        return new AuthResponse("Utilisateur créé avec succès", user.getRole().name());
    }

    // ===== LOGIN =====
    public AuthResponse login(AuthRequest request) {

        Utilisateur user = utilisateurRepos.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        //  AJOUT DU ROLE ICI
        return new AuthResponse("Connexion réussie", user.getRole().name());
    }

    public AuthService(UtilisateurRepos utilisateurRepos, PasswordEncoder passwordEncoder) {
        this.utilisateurRepos = utilisateurRepos;
        this.passwordEncoder = passwordEncoder;
    }
}