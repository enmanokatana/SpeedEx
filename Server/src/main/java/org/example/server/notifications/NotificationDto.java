package org.example.server.notifications;

import java.time.Instant;

public record NotificationDto(String message, Integer to , Instant timestamp) {
}

