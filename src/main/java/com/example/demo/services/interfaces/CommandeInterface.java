package com.example.demo.services.interfaces;

import com.example.demo.dto.CommandeDTO;
import java.util.List;

public interface CommandeInterface {

    CommandeDTO save(CommandeDTO dto);

    CommandeDTO update(Integer id, CommandeDTO dto);

    CommandeDTO findById(Integer id);

    List<CommandeDTO> findAll();

    void delete(Integer id);

    List<CommandeDTO> getAll();

    CommandeDTO getById(Integer id);

    CommandeDTO create(CommandeDTO dto);
}