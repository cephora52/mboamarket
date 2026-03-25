package com.example.demo.controllers;

import com.example.demo.dto.CommandeDTO;
import com.example.demo.services.interfaces.CommandeInterface;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commandes")
public class CommandeController {

    private final CommandeInterface commandeService;

    public CommandeController(CommandeInterface commandeService) {
        this.commandeService = commandeService;
    }

    @PostMapping
    public CommandeDTO create(@RequestBody CommandeDTO dto){
        return commandeService.create(dto);
    }

    @GetMapping("/{id}")
    public CommandeDTO getById(@PathVariable Integer id){
        return commandeService.getById(id);
    }

    @GetMapping
    public List<CommandeDTO> getAll(){
        return commandeService.getAll();
    }

    @PutMapping("/{id}")
    public CommandeDTO update(@PathVariable Integer id,
                              @RequestBody CommandeDTO dto){
        return commandeService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        commandeService.delete(id);
    }
}