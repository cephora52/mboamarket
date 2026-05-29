package com.example.demo.mappers;

import com.example.demo.dto.CommandeProduitDTO;
import com.example.demo.enties.CommandeProduit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = MapperUtils.class, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CommandeProduitMapper {

    @Mapping(source = "commande.idCommande", target = "idCommande")
    @Mapping(source = "produit.idProduit", target = "idProduit")
    @Mapping(source = "produit.nomProduit", target = "nomProduit")
    @Mapping(source = "produit.prix", target = "prix")
    @Mapping(source = "produit.imageProduit", target = "imageProduit")
    CommandeProduitDTO toDTO(CommandeProduit entity);

    @Mapping(target = "commandeProduitPK", ignore = true)
    @Mapping(target = "commande", ignore = true)
    @Mapping(target = "produit", ignore = true)
    CommandeProduit toEntity(CommandeProduitDTO dto);
}