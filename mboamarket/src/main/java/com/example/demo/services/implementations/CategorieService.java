package com.example.demo.services.implementations;

import com.example.demo.dto.CategorieDTO;
import com.example.demo.enties.Categorie;
import com.example.demo.mappers.CategorieMapper;
import com.example.demo.repositories.CategorieRepos;
import com.example.demo.services.interfaces.CategorieInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategorieService implements CategorieInterface {

    private final CategorieRepos categorieRepos;
    private final CategorieMapper mapper;

    public CategorieService(CategorieRepos categorieRepos,
                            CategorieMapper mapper) {
        this.categorieRepos = categorieRepos;
        this.mapper = mapper;
    }

    @Override
    public CategorieDTO create(CategorieDTO dto) {

        Categorie categorie = mapper.toEntity(dto);

        categorie = categorieRepos.save(categorie);

        return mapper.toDTO(categorie);
    }

    @Override
    public CategorieDTO update(Integer id, CategorieDTO dto) {

        Categorie categorie = categorieRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));

        categorie.setNomCategorie(dto.getNomCategorie());

        categorie = categorieRepos.save(categorie);

        return mapper.toDTO(categorie);
    }

    @Override
    public CategorieDTO getById(Integer id) {

        return categorieRepos.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
    }

    @Override
    public List<CategorieDTO> getAll() {

        return categorieRepos.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {

        Categorie categorie = categorieRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));

        categorieRepos.delete(categorie);
    }
}