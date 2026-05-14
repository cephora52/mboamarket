package com.example.demo.repositories;

import com.example.demo.enties.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommandeRepos extends JpaRepository<Commande, Integer> {
    
    @Query("SELECT c FROM Commande c WHERE c.idDistributeur.idUtilisateur = :id")
    List<Commande> findByIdDistributeurIdUtilisateur(@Param("id") Integer id);
    
    @Query("SELECT c FROM Commande c LEFT JOIN c.idAgriculteur a WHERE a.idUtilisateur = :id OR a IS NULL")
    List<Commande> findByIdAgriculteurIdUtilisateur(@Param("id") Integer id);
}
