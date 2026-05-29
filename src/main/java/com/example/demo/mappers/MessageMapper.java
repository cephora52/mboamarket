package com.example.demo.mappers;

import com.example.demo.dto.MessageDTO;
import com.example.demo.enties.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = MapperUtils.class)
public interface MessageMapper {

    @Mapping(source = "idConversation", target = "conversation")
    @Mapping(source = "idExpediteur", target = "expediteur")
    @Mapping(source = "idDestinataire", target = "destinataire")
    @Mapping(source = "read", target = "lu")
    Message toEntity(MessageDTO dto);

    @Mapping(source = "conversation.idConversation", target = "idConversation")
    @Mapping(source = "expediteur.idUtilisateur", target = "idExpediteur")
    @Mapping(source = "destinataire.idUtilisateur", target = "idDestinataire")
    @Mapping(source = "lu", target = "read")
    MessageDTO toDTO(Message entity);

}