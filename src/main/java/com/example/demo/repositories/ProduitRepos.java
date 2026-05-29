package com.example.demo.repositories;

import com.example.demo.enties.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProduitRepos extends JpaRepository<Produit, Integer> {

    @Query("SELECT p FROM Produit p WHERE p.idAgriculteur.idUtilisateur = :id")
    List<Produit> findByIdAgriculteurIdUtilisateur(@Param("id") Integer id);

    Produit findByNomProduit(String nomProduit);

    @Query("SELECT p FROM Produit p WHERE p.idCategorie.idCategorie = :id")
    List<Produit> findByIdCategorie(@Param("id") Integer id);

}
