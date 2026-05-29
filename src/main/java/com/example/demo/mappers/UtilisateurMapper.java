package com.example.demo.mappers;

import com.example.demo.dto.UtilisateurDTO;
import com.example.demo.enties.Utilisateur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = MapperUtils.class, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UtilisateurMapper {

    @Mapping(target = "reliabilityScore", expression = "java(entity.getIndiceFiabilite() != null ? entity.getIndiceFiabilite().getTauxCommande() : 4.8)")
    UtilisateurDTO toDTO(Utilisateur entity);

    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "password", ignore = true)
    Utilisateur toEntity(UtilisateurDTO dto);
}