package com.example.demo.mappers;

import com.example.demo.dto.PaiementDTO;
import com.example.demo.enties.Paiement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaiementMapper {

    PaiementDTO toDTO(Paiement entity);

    Paiement toEntity(PaiementDTO dto);
}
