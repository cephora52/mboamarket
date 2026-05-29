package com.example.demo.controllers;

import com.example.demo.dto.CheckoutRequestDTO;
import com.example.demo.dto.CommandeDTO;
import com.example.demo.services.interfaces.CommandeInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PutMapping("/{id}/status")
    public CommandeDTO updateStatus(@PathVariable Integer id,
                                    @RequestBody String status) {
        return service.updateStatus(id, status);
    }

    @PutMapping("/{id}/preparer")
    public CommandeDTO preparerLivraison(@PathVariable Integer id) {
        return service.preparerLivraison(id);
    }

    @PutMapping("/{id}/demander-confirmation")
    public CommandeDTO demanderConfirmation(@PathVariable Integer id) {
        return service.demanderConfirmation(id);
    }

    @PutMapping("/{id}/valider-agriculteur")
    public CommandeDTO validerParAgriculteur(@PathVariable Integer id) {
        return service.validerParAgriculteur(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAllErrors(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", e.getMessage() != null ? e.getMessage() : "Erreur interne"));
    }
}