package com.example.demo.mappers;

import com.example.demo.dto.UtilisateurDTO;
import com.example.demo.enties.Utilisateur;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = MapperUtils.class)
public interface UtilisateurMapper {

    static UtilisateurDTO toDTO(Utilisateur entity) {
        return null;
    }

    static Utilisateur toEntity(UtilisateurDTO dto) {
        return null;
    }
}
