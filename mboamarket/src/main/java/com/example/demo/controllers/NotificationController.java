package com.example.demo.controllers;

import com.example.demo.dto.NotificationDTO;
import com.example.demo.services.interfaces.NotificationInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationInterface notificationService;

    public NotificationController(NotificationInterface notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/user/{userId}")
    public List<NotificationDTO> getUserNotifications(@PathVariable Integer userId) {
        return notificationService.getByUserId(userId);
    }

    @PutMapping("/{id}/read")
    public NotificationDTO markAsRead(@PathVariable Integer id) {
        return notificationService.markAsRead(id);
    }

    @DeleteMapping("/{id}")
    public void deleteNotification(@PathVariable Integer id) {
        notificationService.deleteNotification(id);
    }
}
