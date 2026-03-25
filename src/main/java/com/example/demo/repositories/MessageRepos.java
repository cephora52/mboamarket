package com.example.demo.repositories;

import com.example.demo.enties.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepos extends JpaRepository<Message, Integer> {

    List<Message> findByConversationIdConversation(Integer idConversation);

}