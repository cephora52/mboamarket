package com.example.demo.mappers;

import com.example.demo.dto.ProduitDTO;
import com.example.demo.enties.Produit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProduitMapper {

    ProduitDTO toDTO(Produit entity);

    Produit toEntity(ProduitDTO dto);
}
