package com.example.demo.mappers;

import com.example.demo.dto.CommandeDTO;
import com.example.demo.enties.Commande;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommandeMapper {

    CommandeDTO toDTO(Commande entity);

    Commande toEntity(CommandeDTO dto);
}
