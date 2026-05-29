package com.example.demo.mappers;

import com.example.demo.dto.PrevisionRecolteDTO;
import com.example.demo.enties.PrevisionRecolte;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = MapperUtils.class)
public interface PrevisionRecolteMapper {

    @Mapping(source = "agriculteur.idUtilisateur", target = "idAgriculteur")
    PrevisionRecolteDTO toDTO(PrevisionRecolte entity);

    @Mapping(source = "idAgriculteur", target = "agriculteur")
    PrevisionRecolte toEntity(PrevisionRecolteDTO dto);
}