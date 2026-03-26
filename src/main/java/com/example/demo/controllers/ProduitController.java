package com.example.demo.controllers;

import com.example.demo.dto.ProduitDTO;
import com.example.demo.services.interfaces.ProduitInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produits")
@RequiredArgsConstructor
public class ProduitController {

    private final ProduitInterface service;

    @PostMapping
    public ProduitDTO create(@RequestBody ProduitDTO dto){
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public ProduitDTO update(@PathVariable Integer id,
                             @RequestBody ProduitDTO dto){
        return service.update(id,dto);
    }

    @GetMapping("/{id}")
    public ProduitDTO get(@PathVariable Integer id){
        return service.findById(id);
    }

    @GetMapping
    public List<ProduitDTO> getAll(){
        return service.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        service.delete(id);
    }

    public ProduitController(ProduitInterface service) {
        this.service = service;
    }
}