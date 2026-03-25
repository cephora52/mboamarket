package com.example.demo.services.interfaces;

import com.example.demo.dto.CategorieDTO;
import java.util.List;

public interface CategorieInterface {

    CategorieDTO create(CategorieDTO dto);

    CategorieDTO update(Integer id, CategorieDTO dto);

    CategorieDTO getById(Integer id);

    List<CategorieDTO> getAll();

    void delete(Integer id);
}