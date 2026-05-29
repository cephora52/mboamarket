package com.example.demo.repositories;

import com.example.demo.enties.CommandeProduit;
import com.example.demo.enties.CommandeProduitPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeProduitRepos extends JpaRepository<CommandeProduit, CommandeProduitPK> {
}
