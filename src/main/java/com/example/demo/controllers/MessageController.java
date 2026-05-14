package com.example.demo.controllers;

import com.example.demo.dto.ConversationDTO;
import com.example.demo.dto.MessageDTO;
import com.example.demo.services.interfaces.MessageInterface;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageInterface messageService;

    public MessageController(MessageInterface messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public MessageDTO sendMessage(@RequestBody MessageDTO dto) {
        return messageService.send(dto);
    }

    @GetMapping("/{userId}")
    public List<ConversationDTO> getConversations(@PathVariable Integer userId) {
        return messageService.getConversations(userId);
    }

    @GetMapping("/conversation/{user1}/{user2}")
    public List<MessageDTO> getMessages(@PathVariable Integer user1, @PathVariable Integer user2) {
        return messageService.getMessagesBetween(user1, user2);
    }

    @PostMapping("/read/{conversationId}/{recipientId}")
    public void markAsRead(@PathVariable Integer conversationId, @PathVariable Integer recipientId) {
        messageService.markAsRead(conversationId, recipientId);
    }

    @PostMapping("/image")
    public MessageDTO sendImage(@RequestBody MessageDTO dto) {
        // We reuse the send method which handles the 'image' field (base64)
        return messageService.send(dto);
    }
}