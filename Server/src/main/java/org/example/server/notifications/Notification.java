package org.example.server.notifications;

import org.example.server.Dtos.UserDto;
import org.example.server.enums.Action;
import org.example.server.models.User;

import java.time.Instant;

public record Notification(String message, UserDto user , Action action, Instant timestamp) {
}

