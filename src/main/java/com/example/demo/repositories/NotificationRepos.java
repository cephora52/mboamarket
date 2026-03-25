package com.example.demo.repositories;

import com.example.demo.enties.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepos extends JpaRepository<Notification, Integer> {
}
