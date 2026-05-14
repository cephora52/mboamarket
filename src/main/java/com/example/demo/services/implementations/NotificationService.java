package com.example.demo.services.implementations;

import com.example.demo.dto.NotificationDTO;
import com.example.demo.enties.Notification;
import com.example.demo.repositories.NotificationRepos;
import com.example.demo.services.interfaces.NotificationInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService implements NotificationInterface {

    private final NotificationRepos notificationRepos;

    public NotificationService(NotificationRepos notificationRepos) {
        this.notificationRepos = notificationRepos;
    }

    @Override
    public List<NotificationDTO> getByUserId(Integer userId) {
        return notificationRepos.findByIdUtilisateurIdUtilisateur(userId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public NotificationDTO markAsRead(Integer notificationId) {
        Notification notification = notificationRepos.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification non trouvée"));
        notification.setLue(true);
        return toDTO(notificationRepos.save(notification));
    }

    @Override
    @Transactional
    public void deleteNotification(Integer notificationId) {
        notificationRepos.deleteById(notificationId);
    }

    private NotificationDTO toDTO(Notification entity) {
        return new NotificationDTO(
                entity.getIdNotification(),
                "Notification", // Entity doesn't have a title field, using default
                entity.getContenuNotification(),
                entity.getDateNotification(),
                entity.getLue() != null && entity.getLue(),
                entity.getIdUtilisateur() != null ? entity.getIdUtilisateur().getIdUtilisateur() : null
        );
    }
}
