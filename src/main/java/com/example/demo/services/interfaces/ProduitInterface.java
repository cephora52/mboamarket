package com.example.demo.services.interfaces;

import com.example.demo.dto.ProduitDTO;

import java.util.List;

public interface ProduitInterface {

    ProduitDTO save(ProduitDTO dto);

    ProduitDTO update(Integer id, ProduitDTO dto);

    ProduitDTO findById(Integer id);

    List<ProduitDTO> findAll();

    void delete(Integer id);
}