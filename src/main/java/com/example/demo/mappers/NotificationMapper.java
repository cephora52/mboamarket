package com.example.demo.mappers;

import com.example.demo.dto.NotificationDTO;
import com.example.demo.enties.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationDTO toDTO(Notification entity);

    Notification toEntity(NotificationDTO dto);
}
