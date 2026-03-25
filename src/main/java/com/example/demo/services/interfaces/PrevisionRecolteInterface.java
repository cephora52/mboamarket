package com.example.demo.services.interfaces;

import com.example.demo.dto.PrevisionRecolteDTO;
import java.util.List;

public interface PrevisionRecolteInterface {

    PrevisionRecolteDTO create(PrevisionRecolteDTO dto);

    PrevisionRecolteDTO update(Integer id, PrevisionRecolteDTO dto);

    void delete(Integer id);

    PrevisionRecolteDTO getById(Integer id);

    List<PrevisionRecolteDTO> getAll();

}