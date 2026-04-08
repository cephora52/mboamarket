package com.example.demo.controllers;

import com.example.demo.dto.UtilisateurDTO;
import com.example.demo.services.interfaces.UtilisateurInterface;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // 🔥 pour Angular
public class UtilisateurController {

    private final UtilisateurInterface service;

    // ===== CREATE =====
    @PostMapping
    public UtilisateurDTO create(@RequestBody UtilisateurDTO dto) {
        return service.save(dto);
    }

    // ===== READ ALL =====
    @GetMapping
    public List<UtilisateurDTO> getAll() {
        return service.findAll();
    }

    // ===== READ BY ID =====
    @GetMapping("/{id}")
    public UtilisateurDTO getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    // ===== UPDATE =====
    @PutMapping("/{id}")
    public UtilisateurDTO update(@PathVariable Integer id,
                                 @RequestBody UtilisateurDTO dto) {
        return service.update(id, dto);
    }

    // ===== DELETE =====
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    public UtilisateurController(UtilisateurInterface service) {
        this.service = service;
    }
}