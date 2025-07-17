package com.example.notification_service.service;

import com.example.notification_service.entity.Notification;
import com.example.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service

@RequiredArgsConstructor

@Slf4j

public class NotificationService {

    private  final NotificationRepository notificationRepository ;

    public  void  addNotification(Notification notification)
    {

        log.info("adding notification to the  db  , message {}", notification.getMessage());

        notificationRepository.save(notification) ;

        // send mailer to sending mails

        // fcm (firebase communication manager  )
    }

}
