package com.example.demo.mappers;

import com.example.demo.dto.ConversationDTO;
import com.example.demo.enties.Conversation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConversationMapper {

    ConversationDTO toDTO(Conversation entity);

    Conversation toEntity(ConversationDTO dto);
}
