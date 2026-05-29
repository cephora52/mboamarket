package com.example.demo.repositories;

import com.example.demo.enties.Conversation;
import com.example.demo.enties.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConversationRepos extends JpaRepository<Conversation, Integer> {
    
    @Query("SELECT c FROM Conversation c WHERE (c.user1 = :u1 AND c.user2 = :u2) OR (c.user1 = :u2 AND c.user2 = :u1)")
    Optional<Conversation> findConversationBetween(@Param("u1") Utilisateur u1, @Param("u2") Utilisateur u2);

    @Query("SELECT c FROM Conversation c WHERE c.user1 = :u OR c.user2 = :u ORDER BY c.dateCreation DESC")
    List<Conversation> findAllByUser(@Param("u") Utilisateur u);
}
