package com.example.demo.services.interfaces;

import com.example.demo.dto.ConversationDTO;
import com.example.demo.dto.MessageDTO;

import java.util.List;

public interface MessageInterface {

    MessageDTO send(MessageDTO dto);

    List<ConversationDTO> getConversations(Integer userId);

    List<MessageDTO> getMessagesBetween(Integer user1Id, Integer user2Id);

    void markAsRead(Integer conversationId, Integer recipientId);
}