package com.example.demo.services.interfaces;

import com.example.demo.dto.CommandeDTO;
import java.util.List;

public interface CommandeInterface {

    CommandeDTO create(CommandeDTO dto);

    CommandeDTO update(Integer id, CommandeDTO dto);

    CommandeDTO getById(Integer id);

    List<CommandeDTO> getAll();

    void delete(Integer id);
}