package com.example.demo.services.interfaces;

import com.example.demo.dto.IndiceFiabiliteDTO;

import java.util.List;

public interface IndiceFiabiliteInterface {

    IndiceFiabiliteDTO create(IndiceFiabiliteDTO dto);

    IndiceFiabiliteDTO update(Integer id, IndiceFiabiliteDTO dto);

    IndiceFiabiliteDTO getById(Integer id);

    List<IndiceFiabiliteDTO> getAll();

    void delete(Integer id);
}