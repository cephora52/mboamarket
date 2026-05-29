package com.example.demo.services.interfaces;

import com.example.demo.dto.RecuDTO;
import java.util.List;

public interface RecuInterface {

    RecuDTO create(RecuDTO dto);

    RecuDTO update(Integer id, RecuDTO dto);

    RecuDTO getById(Integer id);

    List<RecuDTO> getAll();

    void delete(Integer id);
}