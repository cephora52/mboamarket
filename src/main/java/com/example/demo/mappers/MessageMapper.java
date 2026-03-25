package com.example.demo.mappers;

import com.example.demo.dto.MessageDTO;
import com.example.demo.enties.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = MapperUtils.class)
public interface MessageMapper {

    @Mapping(source = "idConversation", target = "conversation")
    @Mapping(source = "idExpediteur", target = "expediteur")
    Message toEntity(MessageDTO dto);

    @Mapping(source = "conversation", target = "idConversation")
    @Mapping(source = "expediteur", target = "idExpediteur")
    MessageDTO toDTO(Message entity);

}