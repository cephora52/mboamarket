package com.example.demo.mappers;

import com.example.demo.dto.PaiementDTO;
import com.example.demo.enties.Paiement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = MapperUtils.class, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PaiementMapper {

    @Mapping(source = "idCommande.idCommande", target = "idCommande")
    PaiementDTO toDTO(Paiement entity);

    @Mapping(target = "idCommande", ignore = true)
    Paiement toEntity(PaiementDTO dto);
}