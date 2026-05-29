package com.example.demo;

import com.example.demo.enties.Produit;
import com.example.demo.enties.Utilisateur;
import com.example.demo.enums.Role;
import com.example.demo.repositories.ProduitRepos;
import com.example.demo.repositories.UtilisateurRepos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class MboaMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(MboaMarketApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataMigration(ProduitRepos produitRepos, UtilisateurRepos utilisateurRepos) {
		return args -> {
			try {
				System.out.println(">>> MboaMarket: Vérification de l'intégrité des données...");
				
				// Trouver le premier agriculteur pour la migration
				Utilisateur firstFarmer = utilisateurRepos.findAll().stream()
						.filter(u -> u.getRole() == Role.AGRICULTEUR)
						.findFirst().orElse(null);

				if (firstFarmer != null) {
					List<Produit> orphans = produitRepos.findAll().stream()
							.filter(p -> p.getIdAgriculteur() == null)
							.toList();
					
					if (!orphans.isEmpty()) {
						System.out.println(">>> MboaMarket: Migration de " + orphans.size() + " produits vers " + firstFarmer.getNom());
						orphans.forEach(p -> p.setIdAgriculteur(firstFarmer));
						produitRepos.saveAll(orphans);
					}
				}
				System.out.println(">>> MboaMarket: Migration terminée.");
			} catch (Exception e) {
				System.err.println(">>> MboaMarket: Erreur lors de la migration automatique : " + e.getMessage());
			}
		};
	}

}
