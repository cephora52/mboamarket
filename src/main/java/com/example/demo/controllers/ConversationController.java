package com.example.demo.controllers;

import com.example.demo.dto.ConversationDTO;
import com.example.demo.services.interfaces.ConversationInterface;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conversations")
public class ConversationController {

    private final ConversationInterface service;

    public ConversationController(ConversationInterface service) {
        this.service = service;
    }

    @PostMapping
    public ConversationDTO create(@RequestBody ConversationDTO dto) {
        return service.create(dto);
    }

    @GetMapping("/user/{id}")
    public List<ConversationDTO> getUserConversations(@PathVariable Integer id) {
        return service.getUserConversations(id);
    }
}