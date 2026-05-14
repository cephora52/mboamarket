package com.example.demo.mappers;

import com.example.demo.dto.CommandeDTO;
import com.example.demo.enties.Commande;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MapperUtils.class, CommandeProduitMapper.class})
public interface CommandeMapper {

    @Mapping(source = "idDistributeur", target = "idDistributeur")
    @Mapping(source = "idAgriculteur", target = "idAgriculteur")
    Commande toEntity(CommandeDTO dto);

    @Mapping(source = "idDistributeur.idUtilisateur", target = "idDistributeur")
    @Mapping(source = "idAgriculteur.idUtilisateur", target = "idAgriculteur")
    @Mapping(source = "idAgriculteur.nom", target = "nomAgriculteur")
    @Mapping(source = "idDistributeur.nom", target = "nomDistributeur")
    @Mapping(source = "commandeProduitCollection", target = "items")
    CommandeDTO toDTO(Commande entity);

}