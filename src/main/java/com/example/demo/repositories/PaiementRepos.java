package com.example.demo.repositories;

import com.example.demo.enties.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaiementRepos extends JpaRepository<Paiement, Integer> {
}
