package com.example.demo.services.implementations;

import com.example.demo.dto.MessageDTO;
import com.example.demo.enties.*;
import com.example.demo.mappers.MessageMapper;
import com.example.demo.repositories.*;
import com.example.demo.services.interfaces.MessageInterface;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService implements MessageInterface {

    private final MessageRepos messageRepos;
    private final ConversationRepos conversationRepos;
    private final UtilisateurRepos utilisateurRepos;
    private final MessageMapper mapper;

    public MessageService(MessageRepos messageRepos,
                          ConversationRepos conversationRepos,
                          UtilisateurRepos utilisateurRepos,
                          MessageMapper mapper) {

        this.messageRepos = messageRepos;
        this.conversationRepos = conversationRepos;
        this.utilisateurRepos = utilisateurRepos;
        this.mapper = mapper;
    }

    @Override
    public MessageDTO send(MessageDTO dto) {

        Conversation conversation = conversationRepos.findById(dto.getIdConversation())
                .orElseThrow(() -> new RuntimeException("Conversation non trouvée"));

        Utilisateur user = utilisateurRepos.findById(dto.getIdExpediteur())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Message message = mapper.toEntity(dto);

        message.setConversation(conversation);
        message.setExpediteur(user);
        message.setDateEnvoi(new Date());

        return mapper.toDTO(messageRepos.save(message));
    }

    @Override
    public List<MessageDTO> getConversationMessages(Integer idConversation) {

        return messageRepos.findByConversationIdConversation(idConversation)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}