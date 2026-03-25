package com.example.demo.mappers;

import com.example.demo.dto.CategorieDTO;
import com.example.demo.enties.Categorie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = MapperUtils.class)
public interface CategorieMapper {

    CategorieDTO toDTO(Categorie entity);

    Categorie toEntity(CategorieDTO dto);

}