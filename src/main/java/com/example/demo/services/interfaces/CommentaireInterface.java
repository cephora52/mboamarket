package com.example.demo.services.interfaces;

import com.example.demo.dto.CommentaireDTO;
import java.util.List;

public interface CommentaireInterface {

    CommentaireDTO create(CommentaireDTO dto);

    CommentaireDTO update(Integer id, CommentaireDTO dto);

    void delete(Integer id);

    CommentaireDTO getById(Integer id);

    List<CommentaireDTO> getAll();

    List<CommentaireDTO> getByProduit(Integer idProduit);

}