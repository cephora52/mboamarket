package com.example.demo.controllers;

import com.example.demo.dto.PaiementDTO;
import com.example.demo.services.interfaces.PaiementInterface;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paiements")
public class PaiementController {

    private final PaiementInterface service;

    public PaiementController(PaiementInterface service) {
        this.service = service;
    }

    @PostMapping
    public PaiementDTO create(@RequestBody PaiementDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public PaiementDTO update(@PathVariable Integer id,
                              @RequestBody PaiementDTO dto) {
        return service.update(id, dto);
    }

    @GetMapping("/{id}")
    public PaiementDTO getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @GetMapping
    public List<PaiementDTO> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}