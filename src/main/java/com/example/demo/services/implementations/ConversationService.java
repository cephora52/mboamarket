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

        Conversation conversation = new Conversation();

        List<Utilisateur> users = dto.getParticipants()
                .stream()
                .map(id -> utilisateurRepos.findById(id)
                        .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé")))
                .collect(Collectors.toList());

        conversation.setUtilisateurCollection(users);

        Conversation saved = conversationRepos.save(conversation);

        ConversationDTO response = mapper.toDTO(saved);
        response.setParticipants(
                users.stream().map(Utilisateur::getIdUtilisateur).collect(Collectors.toList())
        );

        return response;
    }

    @Override
    public List<ConversationDTO> getUserConversations(Integer userId) {

        Utilisateur user = utilisateurRepos.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        return user.getConversationCollection()
                .stream()
                .map(conv -> {
                    ConversationDTO dto = mapper.toDTO(conv);
                    dto.setParticipants(
                            conv.getUtilisateurCollection()
                                    .stream()
                                    .map(Utilisateur::getIdUtilisateur)
                                    .collect(Collectors.toList())
                    );
                    return dto;
                })
                .collect(Collectors.toList());
    }
}