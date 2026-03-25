package com.example.demo.controllers;

import com.example.demo.dto.CommentaireDTO;
import com.example.demo.services.interfaces.CommentaireInterface;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commentaires")
public class CommentaireController {

    private final CommentaireInterface service;

    public CommentaireController(CommentaireInterface service) {
        this.service = service;
    }

    @PostMapping
    public CommentaireDTO create(@RequestBody CommentaireDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public CommentaireDTO update(@PathVariable Integer id,
                                 @RequestBody CommentaireDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    public CommentaireDTO getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @GetMapping
    public List<CommentaireDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/produit/{idProduit}")
    public List<CommentaireDTO> getByProduit(@PathVariable Integer idProduit) {
        return service.getByProduit(idProduit);
    }
}