package com.example.demo.repositories;

import com.example.demo.enties.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepos extends JpaRepository<Categorie, Integer> {
}
