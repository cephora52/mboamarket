package com.example.demo.repositories;

import com.example.demo.enties.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepos extends JpaRepository<Commande, Integer> {
}
