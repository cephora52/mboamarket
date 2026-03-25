package com.example.demo.mappers;

import com.example.demo.dto.CommandeProduitDTO;
import com.example.demo.enties.CommandeProduit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommandeProduitMapper {

    CommandeProduitDTO toDTO(CommandeProduit entity);

    CommandeProduit toEntity(CommandeProduitDTO dto);
}
