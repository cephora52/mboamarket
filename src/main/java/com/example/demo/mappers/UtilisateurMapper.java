package com.example.demo.mappers;

import com.example.demo.dto.UtilisateurDTO;
import com.example.demo.enties.Utilisateur;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = MapperUtils.class)
public interface UtilisateurMapper {

    @org.mapstruct.Mapping(target = "reliabilityScore", expression = "java(entity.getIndiceFiabilite() != null ? entity.getIndiceFiabilite().getTauxCommande() : 4.8)")
    UtilisateurDTO toDTO(Utilisateur entity);

    @org.mapstruct.Mapping(target = "dateCreation", ignore = true)
    Utilisateur toEntity(UtilisateurDTO dto);
}
