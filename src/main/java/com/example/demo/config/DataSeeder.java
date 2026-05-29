package com.example.demo.config;

import com.example.demo.enties.Categorie;
import com.example.demo.enties.Produit;
import com.example.demo.enties.Utilisateur;
import com.example.demo.enums.Role;
import com.example.demo.enums.StatutProduit;
import com.example.demo.repositories.CategorieRepos;
import com.example.demo.repositories.ProduitRepos;
import com.example.demo.repositories.UtilisateurRepos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UtilisateurRepos utilisateurRepos;
    private final CategorieRepos categorieRepos;
    private final ProduitRepos produitRepos;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UtilisateurRepos utilisateurRepos, 
                      CategorieRepos categorieRepos, 
                      ProduitRepos produitRepos, 
                      PasswordEncoder passwordEncoder) {
        this.utilisateurRepos = utilisateurRepos;
        this.categorieRepos = categorieRepos;
        this.produitRepos = produitRepos;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Seed Users
        if (utilisateurRepos.count() == 0) {
            System.out.println(">>> MboaMarket: Seeding default users...");

            Utilisateur admin = new Utilisateur();
            admin.setNom("Administrateur");
            admin.setEmail("admin@ecomove.com");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole(Role.ADMIN);
            admin.setTelephone("111111111");
            admin.setVille("Douala");
            utilisateurRepos.save(admin);

            Utilisateur agriculteur = new Utilisateur();
            agriculteur.setNom("Agriculteur Test");
            agriculteur.setEmail("agriculteur@ecomove.com");
            agriculteur.setPassword(passwordEncoder.encode("password"));
            agriculteur.setRole(Role.AGRICULTEUR);
            agriculteur.setTelephone("222222222");
            agriculteur.setVille("Bafoussam");
            utilisateurRepos.save(agriculteur);

            Utilisateur distributeur = new Utilisateur();
            distributeur.setNom("Distributeur Test");
            distributeur.setEmail("distributeur@ecomove.com");
            distributeur.setPassword(passwordEncoder.encode("password"));
            distributeur.setRole(Role.DISTRIBUTEUR);
            distributeur.setTelephone("333333333");
            distributeur.setVille("Yaoundé");
            utilisateurRepos.save(distributeur);
        } else {
            // Ensure admin password is always "admin"
            utilisateurRepos.findByEmail("admin@ecomove.com").ifPresent(admin -> {
                admin.setPassword(passwordEncoder.encode("admin"));
                utilisateurRepos.save(admin);
                System.out.println(">>> MboaMarket: Admin password updated.");
            });
        }

        // 2. Seed Categories
        if (categorieRepos.count() == 0) {
            System.out.println(">>> MboaMarket: Seeding categories...");
            List<String> categories = Arrays.asList("Fruits et Légumes", "Céréales", "Tubercules", "Légumineuses", "Épices et Aromates");
            for (String catName : categories) {
                Categorie cat = new Categorie();
                cat.setNomCategorie(catName);
                categorieRepos.save(cat);
            }
        }

        // 3. Seed Products - add missing products if count is low
        long existingCount = produitRepos.count();
        if (existingCount < 15) {
            System.out.println(">>> MboaMarket: Seeding products... (existing: " + existingCount + ")");
            Utilisateur farmer = utilisateurRepos.findAll().stream()
                    .filter(u -> u.getRole() == Role.AGRICULTEUR)
                    .findFirst().orElse(null);

            List<Categorie> allCats = categorieRepos.findAll();
            Categorie fruits = allCats.stream().filter(c -> c.getNomCategorie().contains("Fruits")).findFirst().orElse(null);
            Categorie cereales = allCats.stream().filter(c -> c.getNomCategorie().contains("Céréales")).findFirst().orElse(null);
            Categorie tubercules = allCats.stream().filter(c -> c.getNomCategorie().contains("Tubercules")).findFirst().orElse(null);
            Categorie legumineuses = allCats.stream().filter(c -> c.getNomCategorie().contains("Légumineuses")).findFirst().orElse(null);
            Categorie epices = allCats.stream().filter(c -> c.getNomCategorie().contains("Épices")).findFirst().orElse(null);

            if (farmer != null) {
                // Fruits et Légumes
                if (fruits != null) {
                    saveProduct("Tomates Cœur de Bœuf", 2500, 80, "Cageot de 20kg", fruits, farmer, "Yaoundé", StatutProduit.DISPONIBLE,
                            "https://images.unsplash.com/photo-1592924357228-91a4daadcfea?w=600&q=80");
                    saveProduct("Bananes Plantain Mûres", 1500, 120, "Régime", fruits, farmer, "Mbalmayo", StatutProduit.DISPONIBLE,
                            "https://images.unsplash.com/photo-1571771894821-ce9b6c11b0e2?w=600&q=80");
                    saveProduct("Avocats Hass", 3500, 60, "Cageot de 15kg", fruits, farmer, "Bafoussam", StatutProduit.DISPONIBLE,
                            "https://images.unsplash.com/photo-1523049673857-eb18f1d7b578?w=600&q=80");
                    saveProduct("Ananas Victoria", 2000, 45, "Pièce", fruits, farmer, "Ngaoundéré", StatutProduit.DISPONIBLE,
                            "https://images.unsplash.com/photo-1550258987-190a2d41a8ba?w=600&q=80");
                    saveProduct("Mangues Kent", 1800, 90, "Cageot de 10kg", fruits, farmer, "Garoua", StatutProduit.DISPONIBLE,
                            "https://images.unsplash.com/photo-1553279768-865429fa0078?w=600&q=80");
                }

                // Céréales
                if (cereales != null) {
                    saveProduct("Maïs Jaune du Nord", 15000, 100, "Sac de 50kg", cereales, farmer, "Garoua", StatutProduit.DISPONIBLE,
                            "https://images.unsplash.com/photo-1551754655-cd27e38d2076?w=600&q=80");
                    saveProduct("Riz Paddy Blanc", 22000, 75, "Sac de 50kg", cereales, farmer, "Yagoua", StatutProduit.DISPONIBLE,
                            "https://images.unsplash.com/photo-1586201375761-83865001e31c?w=600&q=80");
                    saveProduct("Sorgho Rouge", 12000, 50, "Sac de 50kg", cereales, farmer, "Maroua", StatutProduit.DISPONIBLE,
                            "https://images.unsplash.com/photo-1594375727412-6f0df2a2b4b8?w=600&q=80");
                }

                // Tubercules
                if (tubercules != null) {
                    saveProduct("Manioc Frais", 5000, 50, "Sac", tubercules, farmer, "Bertoua", StatutProduit.DISPONIBLE,
                            "https://images.unsplash.com/photo-1590165482129-1b8bb8c3cb95?q=80&w=600");
                    saveProduct("Ignames de Bafia", 8000, 40, "Sac de 25kg", tubercules, farmer, "Bafia", StatutProduit.DISPONIBLE,
                            "https://media.istockphoto.com/id/510479044/photo/yams.jpg?s=612x612&w=0&k=20&c=HNVwn0T8Yp5h-pg5PCrL7O1jCfoWYbtIt-nRG8YkKJw=");
                    saveProduct("Patates Douces", 4000, 65, "Sac", tubercules, farmer, "Bamenda", StatutProduit.DISPONIBLE,
                            "https://images.unsplash.com/photo-1590779033100-9f8a3a250ffc?w=600&q=80");
                    saveProduct("Macabo Taro", 5500, 35, "Sac de 20kg", tubercules, farmer, "Dschang", StatutProduit.DISPONIBLE,
                            "https://media.istockphoto.com/id/1214380315/photo/taro-roots.jpg?s=612x612&w=0&k=20&c=iR7nF8X9y0Bh4XO1nY0p5JZ0v0t0q0w0r0y0u0i0p0=");
                }

                // Légumineuses
                if (legumineuses != null) {
                    saveProduct("Haricots Rouges", 7500, 55, "Sac de 25kg", legumineuses, farmer, "Bafang", StatutProduit.DISPONIBLE,
                            "https://images.unsplash.com/photo-1565557623262-b51c2513a641?w=600&q=80");
                    saveProduct("Arachides Coques", 6500, 70, "Sac de 20kg", legumineuses, farmer, "Kaélé", StatutProduit.DISPONIBLE,
                            "https://images.unsplash.com/photo-1604065453563-eeed1de9d7b2?w=600&q=80");
                    saveProduct("Niébé (Cornilles)", 6000, 40, "Sac de 25kg", legumineuses, farmer, "Mokolo", StatutProduit.DISPONIBLE,
                            "https://media.istockphoto.com/id/1335349692/photo/black-eyed-peas.jpg?s=612x612&w=0&k=20&c=j3L8nF9X8y0Bh4XO1nY0p5JZ0v0t0q0w0r0y0u0i0p0=");
                }

                // Épices et Aromates
                if (epices != null) {
                    saveProduct("Piment Frais Rouge", 3000, 30, "Sac de 5kg", epices, farmer, "Foumban", StatutProduit.DISPONIBLE,
                            "https://images.unsplash.com/photo-1592135154805-3e9c0b3ddb1c?w=600&q=80");
                    saveProduct("Gingembre Bio", 4500, 25, "Sac de 10kg", epices, farmer, "Bamenda", StatutProduit.DISPONIBLE,
                            "https://images.unsplash.com/photo-1615485500704-8e9900c22f1b?w=600&q=80");
                    saveProduct("Feuilles de Laurier", 2000, 15, "Sac de 2kg", epices, farmer, "Douala", StatutProduit.DISPONIBLE,
                            "https://media.istockphoto.com/id/688944472/photo/bay-leaves.jpg?s=612x612&w=0&k=20&c=h3L8nF9X8y0Bh4XO1nY0p5JZ0v0t0q0w0r0y0u0i0p0=");
                }
            }
        }
        
        System.out.println(">>> MboaMarket: Data seeding completed.");
    }

    private void saveProduct(String nom, double prix, int qte, String unite,
                              Categorie categorie, Utilisateur farmer, String localite,
                              StatutProduit statut, String imageUrl) {
        if (produitRepos.findByNomProduit(nom) != null) return;
        Produit p = new Produit();
        p.setNomProduit(nom);
        p.setPrix(prix);
        p.setQteProduit(qte);
        p.setUniteMesure(unite);
        p.setStatutProduit(statut);
        p.setIdCategorie(categorie);
        p.setIdAgriculteur(farmer);
        p.setLocalite(localite);
        p.setDatePublication(new Date());
        p.setImageProduit(imageUrl);
        produitRepos.save(p);
    }
}
