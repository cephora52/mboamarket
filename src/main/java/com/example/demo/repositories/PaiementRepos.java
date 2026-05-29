package com.example.demo.repositories;

import com.example.demo.enties.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaiementRepos extends JpaRepository<Paiement, Integer> {

    @Query("""
           SELECT p FROM Paiement p
           WHERE p.idCommande.idDistributeur.idUtilisateur = :idUser
           """)
    List<Paiement> findByDistributeur(@Param("idUser") Integer idUser);

    @Query("""
           SELECT DISTINCT p FROM Paiement p
           JOIN p.idCommande c
           JOIN c.commandeProduitCollection cp
           JOIN cp.produit pr
           WHERE pr.idAgriculteur.idUtilisateur = :idUser
           """)
    List<Paiement> findByAgriculteur(@Param("idUser") Integer idUser);

}