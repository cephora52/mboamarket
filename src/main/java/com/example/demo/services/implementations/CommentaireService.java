package com.example.demo.services.implementations;

import com.example.demo.dto.CommentaireDTO;
import com.example.demo.enties.Commentaire;
import com.example.demo.enties.Produit;
import com.example.demo.enties.Utilisateur;
import com.example.demo.mappers.CommentaireMapper;
import com.example.demo.repositories.CommentaireRepos;
import com.example.demo.repositories.ProduitRepos;
import com.example.demo.repositories.UtilisateurRepos;
import com.example.demo.services.interfaces.CommentaireInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentaireService implements CommentaireInterface {

    private final CommentaireRepos commentaireRepos;
    private final ProduitRepos produitRepos;
    private final UtilisateurRepos utilisateurRepos;
    private final CommentaireMapper mapper;

    public CommentaireService(
            CommentaireRepos commentaireRepos,
            ProduitRepos produitRepos,
            UtilisateurRepos utilisateurRepos,
            CommentaireMapper mapper
    ) {
        this.commentaireRepos = commentaireRepos;
        this.produitRepos = produitRepos;
        this.utilisateurRepos = utilisateurRepos;
        this.mapper = mapper;
    }

    @Override
    public CommentaireDTO create(CommentaireDTO dto) {

        Produit produit = produitRepos.findById(dto.getIdProduit())
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        Utilisateur user = utilisateurRepos.findById(dto.getIdDistributeur())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Commentaire commentaire = mapper.toEntity(dto);

        commentaire.setIdProduit(produit);
        commentaire.setIdDistributeur(user);

        return mapper.toDTO(commentaireRepos.save(commentaire));
    }

    @Override
    public CommentaireDTO update(Integer id, CommentaireDTO dto) {

        Commentaire commentaire = commentaireRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Commentaire non trouvé"));

        commentaire.setContenu(dto.getContenu());

        return mapper.toDTO(commentaireRepos.save(commentaire));
    }

    @Override
    public void delete(Integer id) {

        Commentaire commentaire = commentaireRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Commentaire non trouvé"));

        commentaireRepos.delete(commentaire);
    }

    @Override
    public CommentaireDTO getById(Integer id) {

        return mapper.toDTO(
                commentaireRepos.findById(id)
                        .orElseThrow(() -> new RuntimeException("Commentaire non trouvé"))
        );
    }

    @Override
    public List<CommentaireDTO> getAll() {

        return commentaireRepos.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentaireDTO> getByProduit(Integer idProduit) {

        return commentaireRepos.findAll()
                .stream()
                .filter(c -> c.getIdProduit().getIdProduit().equals(idProduit))
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}