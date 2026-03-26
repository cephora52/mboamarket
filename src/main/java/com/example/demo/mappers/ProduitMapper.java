package com.example.demo.mappers;

import com.example.demo.dto.ProduitDTO;
import com.example.demo.enties.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = MapperUtils.class)
public interface ProduitMapper {

    @Mapping(source = "idCategorie.idCategorie", target = "idCategorie")
    @Mapping(source = "idAgriculteur.idUtilisateur", target = "idAgriculteur")
    ProduitDTO toDTO(Produit produit);

    @Mapping(source = "idCategorie", target = "idCategorie")
    @Mapping(source = "idAgriculteur", target = "idAgriculteur")
    Produit toEntity(ProduitDTO dto);
}