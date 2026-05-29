package com.example.demo.services.interfaces;

import com.example.demo.dto.NotificationDTO;
import java.util.List;

public interface NotificationInterface {
    List<NotificationDTO> getByUserId(Integer userId);
    NotificationDTO markAsRead(Integer notificationId);
    void deleteNotification(Integer notificationId);
    NotificationDTO create(Integer userId, String titre, String message);
}
