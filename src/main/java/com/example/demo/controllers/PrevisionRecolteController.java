package com.example.demo.controllers;

import com.example.demo.dto.PrevisionRecolteDTO;
import com.example.demo.services.interfaces.PrevisionRecolteInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/previsions")
@RequiredArgsConstructor
public class PrevisionRecolteController {

    private final PrevisionRecolteInterface service;

    @PostMapping
    public PrevisionRecolteDTO create(@RequestBody PrevisionRecolteDTO dto){
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public PrevisionRecolteDTO update(@PathVariable Integer id,
                                      @RequestBody PrevisionRecolteDTO dto){
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        service.delete(id);
    }

    @GetMapping("/{id}")
    public PrevisionRecolteDTO getById(@PathVariable Integer id){
        return service.getById(id);
    }

    @GetMapping
    public List<PrevisionRecolteDTO> getAll(){
        return service.getAll();
    }

    public PrevisionRecolteController(PrevisionRecolteInterface service) {
        this.service = service;
    }
}