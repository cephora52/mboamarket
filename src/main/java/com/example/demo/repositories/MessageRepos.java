package com.example.demo.repositories;

import com.example.demo.enties.Conversation;
import com.example.demo.enties.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepos extends JpaRepository<Message, Integer> {

    @Query("SELECT m FROM Message m WHERE m.conversation.idConversation = :idConv ORDER BY m.dateEnvoi ASC")
    List<Message> findAllByConversationId(@Param("idConv") Integer idConv);

    List<Message> findByConversationIdConversationAndDestinataireIdUtilisateurAndLuFalse(Integer idConversation, Integer idDestinataire);
}