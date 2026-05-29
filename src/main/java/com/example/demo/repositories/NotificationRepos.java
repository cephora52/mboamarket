package com.example.demo.repositories;

import com.example.demo.enties.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepos extends JpaRepository<Notification, Integer> {

    @Query("SELECT n FROM Notification n WHERE n.idUtilisateur.idUtilisateur = :id ORDER BY n.dateNotification DESC")
    List<Notification> findByIdUtilisateurIdUtilisateurOrderByDateDesc(@Param("id") Integer idUtilisateur);
}
