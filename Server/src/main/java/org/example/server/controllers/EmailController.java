package org.example.server.controllers;

import lombok.RequiredArgsConstructor;
import org.example.server.Dtos.EmailRequest;
import org.example.server.services.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mail")
public class EmailController {
    private final EmailService emailService;


    @PostMapping()
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        if (emailRequest.toEmail() == null || emailRequest.subject() == null || emailRequest.body() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing required parameters");
        }
        emailService.sendSimpleEmail(emailRequest.toEmail(), emailRequest.subject(), emailRequest.body());
        return ResponseEntity.ok("Email sent successfully");
    }
}
