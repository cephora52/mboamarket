package com.example.demo.repositories;

import com.example.demo.enties.Categorie;
import com.example.demo.enties.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepos extends JpaRepository<Produit, Integer> {
    boolean existsByIdCategorie(Categorie categorie);
}
