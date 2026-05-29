package com.example.demo.repositories;

import com.example.demo.enties.PanierItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PanierRepos extends JpaRepository<PanierItem, Integer> {
    List<PanierItem> findByUtilisateurIdUtilisateur(Integer idUtilisateur);
    Optional<PanierItem> findByUtilisateurIdUtilisateurAndProduitIdProduit(Integer idUtilisateur, Integer idProduit);
    void deleteByUtilisateurIdUtilisateurAndProduitIdProduit(Integer idUtilisateur, Integer idProduit);
    void deleteByUtilisateurIdUtilisateur(Integer idUtilisateur);
}
