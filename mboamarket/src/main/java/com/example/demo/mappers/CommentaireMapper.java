package com.example.demo.mappers;

import com.example.demo.dto.CommentaireDTO;
import com.example.demo.enties.Commentaire;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = MapperUtils.class)
public interface CommentaireMapper {

    @Mapping(source = "idProduit.idProduit", target = "idProduit")
    @Mapping(source = "idDistributeur.idUtilisateur", target = "idUtilisateur")
    CommentaireDTO toDTO(Commentaire entity);

    @Mapping(target = "idProduit", ignore = true)
    @Mapping(target = "idDistributeur", ignore = true)
    Commentaire toEntity(CommentaireDTO dto);
}
