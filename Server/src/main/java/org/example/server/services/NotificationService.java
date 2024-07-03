package org.example.server.services;

import lombok.RequiredArgsConstructor;
import org.example.server.models.Notification;
import org.example.server.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;


    public Notification CreateNotification(Notification notification)
    {
        notificationRepository.save(notification);
        return null;
    }
}
