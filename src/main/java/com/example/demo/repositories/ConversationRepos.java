package com.example.demo.repositories;

import com.example.demo.enties.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepos extends JpaRepository<Conversation, Integer> {
}
