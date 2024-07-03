package org.example.server.repositories;

import jakarta.transaction.Transactional;
import org.example.server.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    @Transactional
    void deleteByCreatedAtBefore(LocalDateTime cutoffDate);
}
