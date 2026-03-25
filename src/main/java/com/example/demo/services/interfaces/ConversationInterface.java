package com.example.demo.services.interfaces;

import com.example.demo.dto.ConversationDTO;

import java.util.List;

public interface ConversationInterface {

    ConversationDTO create(ConversationDTO dto);

    List<ConversationDTO> getUserConversations(Integer userId);

}