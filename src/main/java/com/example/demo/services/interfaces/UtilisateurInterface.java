package com.example.demo.services.interfaces;

import com.example.demo.dto.UtilisateurDTO;
import java.util.List;

public interface UtilisateurInterface {

    UtilisateurDTO create(UtilisateurDTO dto);

    UtilisateurDTO getById(Integer id);

    List<UtilisateurDTO> getAll();

    UtilisateurDTO update(Integer id, UtilisateurDTO dto);

    void delete(Integer id);
}