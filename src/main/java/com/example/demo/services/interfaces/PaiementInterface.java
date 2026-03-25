package com.example.demo.services.interfaces;

import com.example.demo.dto.PaiementDTO;
import java.util.List;

public interface PaiementInterface {

    PaiementDTO create(PaiementDTO dto);

    PaiementDTO update(Integer id, PaiementDTO dto);

    PaiementDTO getById(Integer id);

    List<PaiementDTO> getAll();

    void delete(Integer id);
}