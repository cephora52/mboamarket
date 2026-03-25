package com.example.demo.services.implementations;

import com.example.demo.dto.PrevisionRecolteDTO;
import com.example.demo.enties.PrevisionRecolte;
import com.example.demo.enties.Utilisateur;
import com.example.demo.mappers.PrevisionRecolteMapper;
import com.example.demo.repositories.PrevisionRecolteRepos;
import com.example.demo.repositories.UtilisateurRepos;
import com.example.demo.services.interfaces.PrevisionRecolteInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrevisionRecolteService implements PrevisionRecolteInterface {

    private final PrevisionRecolteRepos previsionRepos;
    private final UtilisateurRepos utilisateurRepos;
    private final PrevisionRecolteMapper mapper;

    public PrevisionRecolteService(PrevisionRecolteRepos previsionRepos, UtilisateurRepos utilisateurRepos, PrevisionRecolteMapper mapper) {
        this.previsionRepos = previsionRepos;
        this.utilisateurRepos = utilisateurRepos;
        this.mapper = mapper;
    }

    @Override
    public PrevisionRecolteDTO create(PrevisionRecolteDTO dto) {

        Utilisateur agriculteur = utilisateurRepos.findById(dto.idAgriculteur)
                .orElseThrow(() -> new RuntimeException("Agriculteur non trouvé"));

        PrevisionRecolte entity = mapper.toEntity(dto);
        entity.setAgriculteur(agriculteur);

        return mapper.toDTO(previsionRepos.save(entity));
    }

    @Override
    public PrevisionRecolteDTO update(Integer id, PrevisionRecolteDTO dto) {

        PrevisionRecolte prevision = previsionRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Prévision non trouvée"));

        prevision.setNomProduitPrevu(dto.nomProduitPrevu);
        prevision.setQteEstime(dto.qteEstime);
        prevision.setDateDisponibilite(dto.dateDisponibilite);

        return mapper.toDTO(previsionRepos.save(prevision));
    }

    @Override
    public void delete(Integer id) {
        previsionRepos.deleteById(id);
    }

    @Override
    public PrevisionRecolteDTO getById(Integer id) {
        return mapper.toDTO(
                previsionRepos.findById(id)
                        .orElseThrow(() -> new RuntimeException("Prévision non trouvée"))
        );
    }

    @Override
    public List<PrevisionRecolteDTO> getAll() {
        return previsionRepos.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}