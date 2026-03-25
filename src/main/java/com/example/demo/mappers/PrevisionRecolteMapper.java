package com.example.demo.mappers;

import com.example.demo.dto.PrevisionRecolteDTO;
import com.example.demo.enties.PrevisionRecolte;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrevisionRecolteMapper {

    PrevisionRecolteDTO toDTO(PrevisionRecolte entity);

    PrevisionRecolte toEntity(PrevisionRecolteDTO dto);
}
