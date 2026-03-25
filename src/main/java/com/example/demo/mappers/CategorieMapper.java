package com.example.demo.mappers;

import com.example.demo.dto.CategorieDTO;
import com.example.demo.enties.Categorie;

public interface CategorieMapper {

    public static CategorieDTO toDTO(Categorie entity) {
        if (entity == null) return null;

        CategorieDTO dto = new CategorieDTO();
        dto.setIdCategorie(entity.getIdCategorie());
        dto.setNomCategorie(entity.getNomCategorie());

        return dto;
    }

    public static Categorie toEntity(CategorieDTO dto) {
        if (dto == null) return null;

        Categorie entity = new Categorie();
        entity.setIdCategorie(dto.getIdCategorie());
        entity.setNomCategorie(dto.getNomCategorie());

        return entity;
    }
}
