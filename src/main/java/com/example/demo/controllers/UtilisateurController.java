package com.example.demo.controllers;

import com.example.demo.dto.UtilisateurDTO;
import com.example.demo.services.interfaces.UtilisateurInterface;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UtilisateurInterface service;

    @PostMapping
    public UtilisateurDTO create(@RequestBody UtilisateurDTO dto) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    public UtilisateurDTO getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @GetMapping
    public List<UtilisateurDTO> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public UtilisateurDTO update(@PathVariable Integer id,
                                 @RequestBody UtilisateurDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    public UtilisateurController(UtilisateurInterface service) {
        this.service = service;
    }
}