package com.example.demo.services.interfaces;

import com.example.demo.dto.MessageDTO;

import java.util.List;

public interface MessageInterface {

    MessageDTO send(MessageDTO dto);

    List<MessageDTO> getConversationMessages(Integer idConversation);

}