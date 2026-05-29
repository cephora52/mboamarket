package com.example.demo.controllers;

import com.example.demo.dto.CategorieDTO;
import com.example.demo.services.interfaces.CategorieInterface;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategorieController {

    private final CategorieInterface service;

    public CategorieController(CategorieInterface service) {
        this.service = service;
    }

    @PostMapping
    public CategorieDTO create(@RequestBody CategorieDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public CategorieDTO update(@PathVariable Integer id,
                               @RequestBody CategorieDTO dto) {
        return service.update(id, dto);
    }

    @GetMapping("/{id}")
    public CategorieDTO getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @GetMapping
    public List<CategorieDTO> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}