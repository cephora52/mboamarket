package com.example.demo.services.implementations;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.UtilisateurDTO;
import com.example.demo.enties.Utilisateur;
import com.example.demo.mappers.UtilisateurMapper;
import com.example.demo.repositories.UtilisateurRepos;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UtilisateurRepos utilisateurRepos;
    private final UtilisateurMapper utilisateurMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UtilisateurRepos utilisateurRepos, 
                       UtilisateurMapper utilisateurMapper, 
                       PasswordEncoder passwordEncoder) {
        this.utilisateurRepos = utilisateurRepos;
        this.utilisateurMapper = utilisateurMapper;
        this.passwordEncoder = passwordEncoder;
    }

    // ===== REGISTER =====
    public AuthResponse register(UtilisateurDTO dto) {
        System.out.println(">>> MboaMarket: Tentative d'inscription pour: " + dto.getEmail());

        // 1. Validation de sécurité
        if (dto.getNom() == null || dto.getEmail() == null || dto.getPassword() == null || dto.getRole() == null) {
            throw new RuntimeException("Erreur: Tous les champs obligatoires (Nom, Email, Mot de passe, Rôle) doivent être remplis.");
        }

        if (utilisateurRepos.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }

        // 2. Conversion DTO -> Entity via le Mapper
        Utilisateur user = utilisateurMapper.toEntity(dto);

        // 3. Hashage du mot de passe
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        utilisateurRepos.save(user);
        System.out.println(">>> MboaMarket: Utilisateur créé avec succès id=" + user.getIdUtilisateur());

        return new AuthResponse("Utilisateur créé avec succès");
    }

    // ===== LOGIN =====
    public AuthResponse login(AuthRequest request) {

        Utilisateur user = utilisateurRepos.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        //  ID, NOM, EMAIL RETOURNÉS
        return new AuthResponse("Connexion réussie", user.getRole().name(), user.getIdUtilisateur(), user.getNom(), user.getEmail());
    }
}