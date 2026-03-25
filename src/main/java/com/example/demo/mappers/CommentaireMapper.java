package com.example.demo.mappers;

import com.example.demo.dto.CommentaireDTO;
import com.example.demo.enties.Commentaire;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentaireMapper {

    CommentaireDTO toDTO(Commentaire entity);

    Commentaire toEntity(CommentaireDTO dto);
}
