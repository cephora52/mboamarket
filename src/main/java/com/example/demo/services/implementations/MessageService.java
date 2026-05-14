package com.example.demo.services.implementations;

import com.example.demo.dto.ConversationDTO;
import com.example.demo.dto.MessageDTO;
import com.example.demo.enties.*;
import com.example.demo.enums.Role;
import com.example.demo.mappers.MessageMapper;
import com.example.demo.repositories.*;
import com.example.demo.services.interfaces.MessageInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    @Transactional
    public MessageDTO send(MessageDTO dto) {
        try {
            if (dto.getIdExpediteur() == null || dto.getIdDestinataire() == null) {
                throw new RuntimeException("L'ID de l'expéditeur ou du destinataire est NULL.");
            }

            Utilisateur sender = utilisateurRepos.findById(dto.getIdExpediteur())
                    .orElseThrow(() -> new RuntimeException("Expéditeur (ID=" + dto.getIdExpediteur() + ") non trouvé"));
            Utilisateur receiver = utilisateurRepos.findById(dto.getIdDestinataire())
                    .orElseThrow(() -> new RuntimeException("Destinataire (ID=" + dto.getIdDestinataire() + ") non trouvé"));

            System.out.println("MboaMarket: Tentative d'envoi de " + sender.getIdUtilisateur() + " (" + sender.getRole() + ") vers " + receiver.getIdUtilisateur() + " (" + receiver.getRole() + ")");

            // Récupérer ou créer la conversation
            Conversation conversation;
            Optional<Conversation> existing = conversationRepos.findConversationBetween(sender, receiver);
            if (existing.isPresent()) {
                conversation = existing.get();
                System.out.println("MboaMarket: OK - Conversation trouvée ID=" + conversation.getIdConversation());
            } else {
                conversation = new Conversation(sender, receiver);
                conversation = conversationRepos.save(conversation);
                System.out.println("MboaMarket: OK - Nouvelle conversation créée ID=" + conversation.getIdConversation());
            }

            Message message = new Message();
            message.setContenu(dto.getContenu() == null ? "" : dto.getContenu());
            message.setImage(dto.getImage());
            message.setConversation(conversation);
            message.setExpediteur(sender);
            message.setDestinataire(receiver);
            message.setDateEnvoi(new Date());
            message.setLu(false);

            Message saved = messageRepos.save(message);
            System.out.println("MboaMarket: OK - Message sauvegardé en DB ID=" + saved.getIdMessage() + " pour conv " + conversation.getIdConversation());
            
            MessageDTO result = mapper.toDTO(saved);
            System.out.println("MboaMarket: OK - DTO généré: " + result.getContenu() + " (ID Conv: " + result.getIdConversation() + ")");
            return result;
        } catch (Exception e) {
            System.err.println("MboaMarket: ERREUR CRITIQUE lors de l'envoi du message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur serveur lors de l'enregistrement: " + e.getMessage());
        }
    }

    @Override
    public List<ConversationDTO> getConversations(Integer userId) {
        try {
            Utilisateur user = utilisateurRepos.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Utilisateur ID=" + userId + " non trouvé"));

            List<Conversation> conversations = conversationRepos.findAllByUser(user);
            if (conversations == null) return List.of();

            return conversations.stream().map(c -> {
                ConversationDTO dto = new ConversationDTO();
                dto.setIdConversation(c.getIdConversation());
                dto.setDateCreation(c.getDateCreation());
                
                Utilisateur u1 = c.getUser1();
                Utilisateur u2 = c.getUser2();
                
                if (u1 != null) dto.setIdUser1(u1.getIdUtilisateur());
                if (u2 != null) dto.setIdUser2(u2.getIdUtilisateur());

                Utilisateur other = (u1 != null && u1.equals(user)) ? u2 : u1;
                if (other != null) {
                    dto.setOtherUserName(other.getNom());
                    dto.setOtherUserPhoto(other.getPhoto());
                } else {
                    dto.setOtherUserName("Utilisateur Inconnu");
                }

                // Get last message safely
                try {
                    List<Message> msgs = messageRepos.findAllByConversationId(c.getIdConversation());
                    if (msgs != null && !msgs.isEmpty()) {
                        Message last = msgs.get(msgs.size() - 1);
                        dto.setLastMessage(last.getContenu() != null ? last.getContenu() : "📷 Image");
                    }
                } catch (Exception e) {
                    System.err.println("MboaMarket: Erreur lors de la récupération du dernier message pour conv " + c.getIdConversation());
                }

                return dto;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("MboaMarket: Erreur dans getConversations: " + e.getMessage());
            e.printStackTrace();
            return List.of(); // On renvoie une liste vide au lieu de planter (500)
        }
    }

    @Override
    public List<MessageDTO> getMessagesBetween(Integer u1, Integer u2) {
        try {
            Utilisateur user1 = utilisateurRepos.findById(u1)
                    .orElseThrow(() -> new RuntimeException("Utilisateur 1 non trouvé"));
            Utilisateur user2 = utilisateurRepos.findById(u2)
                    .orElseThrow(() -> new RuntimeException("Utilisateur 2 non trouvé"));

            // On cherche la conversation
            Optional<Conversation> conversation = conversationRepos.findConversationBetween(user1, user2);

            if (conversation.isEmpty()) {
                System.out.println("MboaMarket: Aucune conversation trouvée entre " + u1 + " et " + u2);
                return List.of();
            }

            Integer convId = conversation.get().getIdConversation();
            System.out.println("MboaMarket: Récupération des messages pour la conversation ID=" + convId);

            List<Message> messages = messageRepos.findAllByConversationId(convId);
            System.out.println("MboaMarket: " + messages.size() + " messages trouvés en base.");

            return messages.stream()
                    .map(mapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("MboaMarket: Erreur dans getMessagesBetween: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    @Transactional
    public void markAsRead(Integer conversationId, Integer recipientId) {
        List<Message> unreadMessages = messageRepos.findByConversationIdConversationAndDestinataireIdUtilisateurAndLuFalse(conversationId, recipientId);
        unreadMessages.forEach(m -> m.setLu(true));
        messageRepos.saveAll(unreadMessages);
    }
}