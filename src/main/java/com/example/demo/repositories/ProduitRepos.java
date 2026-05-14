package com.example.demo.repositories;

import com.example.demo.enties.Categorie;
import com.example.demo.enties.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProduitRepos extends JpaRepository<Produit, Integer> {
    boolean existsByIdCategorie(Categorie categorie);

    @Query("SELECT p FROM Produit p WHERE p.idAgriculteur.idUtilisateur = :id OR p.idAgriculteur IS NULL")
    List<Produit> findByIdAgriculteurIdUtilisateur(@Param("id") Integer id);
}
