package com.example.demo.mappers;

import com.example.demo.dto.ProduitDTO;
import com.example.demo.enties.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = MapperUtils.class)
public interface ProduitMapper {

    @Mapping(source = "idCategorie.idCategorie", target = "idCategorie")
    @Mapping(source = "idCategorie.nomCategorie", target = "nomCategorie")
    @Mapping(source = "idAgriculteur.idUtilisateur", target = "idAgriculteur")
    @Mapping(source = "idAgriculteur.nom", target = "nomAgriculteur")
    @Mapping(source = "idAgriculteur.photo", target = "photoAgriculteur")
    @Mapping(source = "imageProduit", target = "photo")
    ProduitDTO toDTO(Produit produit);

    @Mapping(source = "idCategorie", target = "idCategorie")
    @Mapping(source = "idAgriculteur", target = "idAgriculteur")
    @Mapping(source = "photo", target = "imageProduit")
    Produit toEntity(ProduitDTO dto);
}