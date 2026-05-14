package com.example.demo.mappers;

import com.example.demo.dto.ConversationDTO;
import com.example.demo.enties.Conversation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = MapperUtils.class)
public interface ConversationMapper {

    @Mapping(source = "user1.idUtilisateur", target = "idUser1")
    @Mapping(source = "user2.idUtilisateur", target = "idUser2")
    ConversationDTO toDTO(Conversation entity);

    @Mapping(source = "idUser1", target = "user1")
    @Mapping(source = "idUser2", target = "user2")
    Conversation toEntity(ConversationDTO dto);
}
