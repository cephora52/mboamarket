package com.example.demo.mappers;

import com.example.demo.dto.UtilisateurDTO;
import com.example.demo.enties.Utilisateur;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper {

    UtilisateurDTO toDTO(Utilisateur entity);

    Utilisateur toEntity(UtilisateurDTO dto);
}
