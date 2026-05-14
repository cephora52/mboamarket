package com.example.demo.controllers;

import com.example.demo.dto.CheckoutRequestDTO;
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

    @GetMapping("/distributeur/{id}")
    public List<CommandeDTO> getByDistributeur(@PathVariable Integer id) {
        return service.getByDistributeur(id);
    }

    @GetMapping("/agriculteur/{id}")
    public List<CommandeDTO> getByAgriculteur(@PathVariable Integer id) {
        return service.getByAgriculteur(id);
    }

    @PostMapping("/payer")
    public List<CommandeDTO> payerPanier(@RequestBody CheckoutRequestDTO dto) {
        return service.payerPanier(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}