package com.example.demo.mappers;

import com.example.demo.dto.IndiceFiabiliteDTO;
import com.example.demo.enties.IndiceFiabilite;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = MapperUtils.class)
public interface IndiceFiabiliteMapper {

    IndiceFiabiliteDTO toDTO(IndiceFiabilite entity);

    IndiceFiabilite toEntity(IndiceFiabiliteDTO dto);
}
