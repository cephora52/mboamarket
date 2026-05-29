package com.example.demo.mappers;

import com.example.demo.dto.NotificationDTO;
import com.example.demo.enties.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = MapperUtils.class)
public interface NotificationMapper {

    @Mapping(source = "contenuNotification", target = "message")
    @Mapping(source = "lue", target = "lu")
    @Mapping(target = "titre", constant = "Notification")
    @Mapping(source = "idUtilisateur.idUtilisateur", target = "idUtilisateur")
    NotificationDTO toDTO(Notification entity);

    @Mapping(source = "message", target = "contenuNotification")
    @Mapping(source = "lu", target = "lue")
    @Mapping(source = "idUtilisateur", target = "idUtilisateur", ignore = true)
    Notification toEntity(NotificationDTO dto);
}
