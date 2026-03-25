package com.example.demo.mappers;

import com.example.demo.dto.ProduitDTO;
import com.example.demo.enties.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = MapperUtils.class)
public interface ProduitMapper {

    @Mapping(source = "idCategorie", target = "idCategorie")
    @Mapping(source = "idAgriculteur", target = "idAgriculteur")
    ProduitDTO toDTO(Produit entity);

    @Mapping(source = "idCategorie", target = "idCategorie")
    @Mapping(source = "idAgriculteur", target = "idAgriculteur")
    Produit toEntity(ProduitDTO dto);

}