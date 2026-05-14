package com.example.demo.repositories;

import com.example.demo.enties.Favoris;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FavorisRepos extends JpaRepository<Favoris, Integer> {
    List<Favoris> findByUtilisateurIdUtilisateur(Integer idUtilisateur);
    Optional<Favoris> findByUtilisateurIdUtilisateurAndProduitIdProduit(Integer idUtilisateur, Integer idProduit);
    void deleteByUtilisateurIdUtilisateurAndProduitIdProduit(Integer idUtilisateur, Integer idProduit);
}
