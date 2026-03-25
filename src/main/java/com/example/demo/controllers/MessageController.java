package com.example.demo.controllers;

import com.example.demo.dto.MessageDTO;
import com.example.demo.services.interfaces.MessageInterface;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageInterface service;

    public MessageController(MessageInterface service) {
        this.service = service;
    }

    @PostMapping("/send")
    public MessageDTO send(@RequestBody MessageDTO dto) {
        return service.send(dto);
    }

    @GetMapping("/conversation/{id}")
    public List<MessageDTO> getMessages(@PathVariable Integer id) {
        return service.getConversationMessages(id);
    }
}