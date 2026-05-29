package com.example.demo.mappers;

import com.example.demo.dto.CategorieDTO;
import com.example.demo.enties.Categorie;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = MapperUtils.class, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategorieMapper {

    CategorieDTO toDTO(Categorie entity);

    Categorie toEntity(CategorieDTO dto);

}