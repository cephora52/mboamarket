package com.example.demo.services.interfaces;

import com.example.demo.dto.CommandeProduitDTO;
import java.util.List;

public interface CommandeProduitInterface {

    CommandeProduitDTO ajouterProduitDansCommande(CommandeProduitDTO dto);

    List<CommandeProduitDTO> getProduitsCommande(Integer idCommande);

    void supprimerProduitCommande(Integer idCommande, Integer idProduit);
}