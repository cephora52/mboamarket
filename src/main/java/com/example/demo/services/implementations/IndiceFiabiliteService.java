package com.example.demo.services.implementations;

import com.example.demo.dto.IndiceFiabiliteDTO;
import com.example.demo.enties.IndiceFiabilite;
import com.example.demo.enties.Utilisateur;
import com.example.demo.mappers.IndiceFiabiliteMapper;
import com.example.demo.repositories.IndiceFiabiliteRepos;
import com.example.demo.repositories.UtilisateurRepos;
import com.example.demo.services.interfaces.IndiceFiabiliteInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IndiceFiabiliteService implements IndiceFiabiliteInterface {

    private final IndiceFiabiliteRepos repos;
    private final UtilisateurRepos utilisateurRepos;
    private final IndiceFiabiliteMapper mapper;

    public IndiceFiabiliteService(
            IndiceFiabiliteRepos repos,
            UtilisateurRepos utilisateurRepos,
            IndiceFiabiliteMapper mapper
    ) {
        this.repos = repos;
        this.utilisateurRepos = utilisateurRepos;
        this.mapper = mapper;
    }

    private String calculerNiveau(Double taux, Double regularite) {

        if (taux == null || regularite == null) return "FAIBLE";

        if (taux >= 70 && regularite >= 60)
            return "FIABLE";

        if (taux >= 40)
            return "MOYEN";

        return "FAIBLE";
    }

    @Override
    public IndiceFiabiliteDTO create(IndiceFiabiliteDTO dto) {

        Utilisateur agriculteur = utilisateurRepos.findById(dto.getIdAgriculteur())
                .orElseThrow(() -> new RuntimeException("Agriculteur non trouvé"));

        IndiceFiabilite entity = mapper.toEntity(dto);

        entity.setIdAgriculteur(agriculteur);

        entity.setNiveauFiabilite(
                calculerNiveau(entity.getTauxCommande(), entity.getRegularitePublication())
        );

        return mapper.toDTO(repos.save(entity));
    }

    @Override
    public IndiceFiabiliteDTO update(Integer id, IndiceFiabiliteDTO dto) {

        IndiceFiabilite entity = repos.findById(id)
                .orElseThrow(() -> new RuntimeException("Indice non trouvé"));

        entity.setTauxCommande(dto.getTauxCommande());
        entity.setRegularitePublication(dto.getRegularitePublication());

        entity.setNiveauFiabilite(
                calculerNiveau(entity.getTauxCommande(), entity.getRegularitePublication())
        );

        return mapper.toDTO(repos.save(entity));
    }

    @Override
    public IndiceFiabiliteDTO getById(Integer id) {

        return mapper.toDTO(
                repos.findById(id)
                        .orElseThrow(() -> new RuntimeException("Indice non trouvé"))
        );
    }

    @Override
    public List<IndiceFiabiliteDTO> getAll() {

        return repos.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {

        repos.deleteById(id);
    }
}