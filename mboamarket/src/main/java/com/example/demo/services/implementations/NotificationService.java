package com.example.demo.services.implementations;

import com.example.demo.dto.NotificationDTO;
import com.example.demo.enties.Notification;
import com.example.demo.enties.Utilisateur;
import com.example.demo.repositories.NotificationRepos;
import com.example.demo.repositories.UtilisateurRepos;
import com.example.demo.services.interfaces.NotificationInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService implements NotificationInterface {

    private final NotificationRepos notificationRepos;
    private final UtilisateurRepos utilisateurRepos;

    public NotificationService(NotificationRepos notificationRepos,
                               UtilisateurRepos utilisateurRepos) {
        this.notificationRepos = notificationRepos;
        this.utilisateurRepos = utilisateurRepos;
    }

    @Override
    public List<NotificationDTO> getByUserId(Integer userId) {
        return notificationRepos.findByIdUtilisateurIdUtilisateurOrderByDateDesc(userId)
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

    @Override
    @Transactional
    public NotificationDTO create(Integer userId, String titre, String message) {
        Utilisateur user = utilisateurRepos.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Notification n = new Notification();
        n.setTitre(titre);
        n.setContenuNotification(message);
        n.setDateNotification(new Date());
        n.setLue(false);
        n.setIdUtilisateur(user);

        return toDTO(notificationRepos.save(n));
    }

    private NotificationDTO toDTO(Notification entity) {
        return new NotificationDTO(
                entity.getIdNotification(),
                entity.getTitre() != null ? entity.getTitre() : "Notification",
                entity.getContenuNotification(),
                entity.getDateNotification(),
                entity.getLue() != null && entity.getLue(),
                entity.getIdUtilisateur() != null ? entity.getIdUtilisateur().getIdUtilisateur() : null
        );
    }
}
