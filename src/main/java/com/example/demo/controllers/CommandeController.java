package com.example.demo.controllers;

import com.example.demo.dto.CommandeDTO;
import com.example.demo.services.interfaces.CommandeInterface;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commandes")
public class CommandeController {

    private final CommandeInterface service;

    public CommandeController(CommandeInterface service) {
        this.service = service;
    }

    @PostMapping
    public CommandeDTO create(@RequestBody CommandeDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public CommandeDTO update(@PathVariable Integer id,
                              @RequestBody CommandeDTO dto) {
        return service.update(id, dto);
    }

    @GetMapping("/{id}")
    public CommandeDTO getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @GetMapping
    public List<CommandeDTO> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}