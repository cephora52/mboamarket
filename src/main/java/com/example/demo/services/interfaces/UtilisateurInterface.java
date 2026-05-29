package com.example.demo.services.interfaces;

import com.example.demo.dto.UtilisateurDTO;
import java.util.List;

public interface UtilisateurInterface {

    UtilisateurDTO save(UtilisateurDTO dto);

    UtilisateurDTO create(UtilisateurDTO dto);

    UtilisateurDTO getById(Integer id);

    List<UtilisateurDTO> getAll();

    UtilisateurDTO update(Integer id, UtilisateurDTO dto);

    UtilisateurDTO findById(Integer id);

    List<UtilisateurDTO> findAll();

    void delete(Integer id);
}