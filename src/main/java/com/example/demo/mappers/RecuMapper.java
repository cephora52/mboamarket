package com.example.demo.mappers;

import com.example.demo.dto.RecuDTO;
import com.example.demo.enties.Recu;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = MapperUtils.class)
public interface RecuMapper {

    RecuDTO toDTO(Recu entity);

    Recu toEntity(RecuDTO dto);
}
