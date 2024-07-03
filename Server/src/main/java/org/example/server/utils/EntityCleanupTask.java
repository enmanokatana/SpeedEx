package org.example.server.utils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.server.models.Exam;
import org.example.server.models.Notification;
import org.example.server.notifications.NotificationController;
import org.example.server.notifications.NotificationDto;
import org.example.server.repositories.ExamRepository;
import org.example.server.repositories.NotificationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class EntityCleanupTask {

    private final NotificationRepository notificationRepository;
    private final NotificationController notificationController;
    private final ExamRepository examRepository;
    @Scheduled(fixedRate = 86400000)
    @Transactional
    public void cleanUpOldEntities(){

        log.info("Starting cleanup task");
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(30);
        log.info("Cutoff date: {}", cutoffDate);
        notificationRepository.deleteByCreatedAtBefore(cutoffDate);
        log.info("Deleted old notifications");
    }

    //Notify Users of their upcoming exams
    @Scheduled(fixedRate =86400000 )
    public void NotifyUserUpComingExams(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime day = now.plusHours(24);

        var allExams = examRepository.findAll();
        for (Exam exam:allExams){
            if (exam.getStudent()!=null  && exam.getPassingDate().isBefore(day) && exam.getPassed()){
                NotificationDto notification = new NotificationDto("exam named :"+exam.getName()+"is tomorrow",exam.getStudent().getId(), Instant.now());
                notificationRepository.save(Notification.builder()
                        .message(notification.message())
                                .createdAt(LocalDateTime.now())
                                .dest(notification.to())
                                .id(0)
                        .build());
                notificationController.sendNotification(notification);
            }

        }



    }
}
