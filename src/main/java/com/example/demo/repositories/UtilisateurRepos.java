package com.example.demo.repositories;

import com.example.demo.enties.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepos extends JpaRepository<Utilisateur, Integer> {
}
