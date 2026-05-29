package com.example.demo.services.implementations;

import com.example.demo.dto.ConversationDTO;
import com.example.demo.enties.Conversation;
import com.example.demo.enties.Utilisateur;
import com.example.demo.mappers.ConversationMapper;
import com.example.demo.repositories.ConversationRepos;
import com.example.demo.repositories.UtilisateurRepos;
import com.example.demo.services.interfaces.ConversationInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConversationService implements ConversationInterface {

    private final ConversationRepos conversationRepos;
    private final UtilisateurRepos utilisateurRepos;
    private final ConversationMapper mapper;

    public ConversationService(ConversationRepos conversationRepos,
                               UtilisateurRepos utilisateurRepos,
                               ConversationMapper mapper) {

        this.conversationRepos = conversationRepos;
        this.utilisateurRepos = utilisateurRepos;
        this.mapper = mapper;
    }

    @Override
    public ConversationDTO create(ConversationDTO dto) {
        Utilisateur user1 = utilisateurRepos.findById(dto.getIdUser1())
                .orElseThrow(() -> new RuntimeException("Utilisateur 1 non trouvé"));
        Utilisateur user2 = utilisateurRepos.findById(dto.getIdUser2())
                .orElseThrow(() -> new RuntimeException("Utilisateur 2 non trouvé"));

        Conversation conversation = new Conversation(user1, user2);
        Conversation saved = conversationRepos.save(conversation);

        return mapper.toDTO(saved);
    }

    @Override
    public List<ConversationDTO> getUserConversations(Integer userId) {
        Utilisateur user = utilisateurRepos.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        return conversationRepos.findAllByUser(user)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}