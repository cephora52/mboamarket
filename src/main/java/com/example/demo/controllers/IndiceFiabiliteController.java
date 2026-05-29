package com.example.demo.controllers;

import com.example.demo.dto.IndiceFiabiliteDTO;
import com.example.demo.services.interfaces.IndiceFiabiliteInterface;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/indice")
public class IndiceFiabiliteController {

    private final IndiceFiabiliteInterface service;

    public IndiceFiabiliteController(IndiceFiabiliteInterface service) {
        this.service = service;
    }

    @PostMapping
    public IndiceFiabiliteDTO create(@RequestBody IndiceFiabiliteDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public IndiceFiabiliteDTO update(@PathVariable Integer id,
                                     @RequestBody IndiceFiabiliteDTO dto) {
        return service.update(id, dto);
    }

    @GetMapping("/{id}")
    public IndiceFiabiliteDTO getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @GetMapping
    public List<IndiceFiabiliteDTO> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}