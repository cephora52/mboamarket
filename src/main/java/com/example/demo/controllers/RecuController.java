package com.example.demo.controllers;

import com.example.demo.dto.RecuDTO;
import com.example.demo.services.interfaces.RecuInterface;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recus")
public class RecuController {

    private final RecuInterface service;

    public RecuController(RecuInterface service) {
        this.service = service;
    }

    @PostMapping
    public RecuDTO create(@RequestBody RecuDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public RecuDTO update(@PathVariable Integer id,
                          @RequestBody RecuDTO dto) {
        return service.update(id, dto);
    }

    @GetMapping("/{id}")
    public RecuDTO getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @GetMapping
    public List<RecuDTO> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}