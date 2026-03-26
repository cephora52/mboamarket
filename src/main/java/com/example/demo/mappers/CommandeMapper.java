package com.example.demo.mappers;

import com.example.demo.dto.CommandeDTO;
import com.example.demo.enties.Commande;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = MapperUtils.class)
public interface CommandeMapper {

    @Mapping(source = "idDistributeur", target = "idDistributeur")
    Commande toEntity(CommandeDTO dto);

    @Mapping(source = "idDistributeur", target = "idDistributeur")
    CommandeDTO toDTO(Commande entity);
}