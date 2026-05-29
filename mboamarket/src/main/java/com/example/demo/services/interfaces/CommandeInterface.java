package com.example.demo.services.interfaces;

import com.example.demo.dto.CheckoutRequestDTO;
import com.example.demo.dto.CommandeDTO;
import java.util.List;

public interface CommandeInterface {

    CommandeDTO create(CommandeDTO dto);

    CommandeDTO update(Integer id, CommandeDTO dto);

    CommandeDTO getById(Integer id);

    List<CommandeDTO> getAll();

    List<CommandeDTO> getByDistributeur(Integer id);

    List<CommandeDTO> getByAgriculteur(Integer id);

    List<CommandeDTO> payerPanier(CheckoutRequestDTO dto);

    CommandeDTO updateStatus(Integer id, String status);

    CommandeDTO preparerLivraison(Integer id);

    CommandeDTO demanderConfirmation(Integer id);

    CommandeDTO validerParAgriculteur(Integer id);

    void delete(Integer id);
}