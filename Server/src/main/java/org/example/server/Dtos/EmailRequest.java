package org.example.server.Dtos;

public record EmailRequest(String toEmail,
                           String subject,
                           String body) {
}
