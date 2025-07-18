package com.example.notification_service.consumer;

import com.example.ConnectionService.event.ConnectionEvent;
import com.example.notification_service.entity.Notification;
import com.example.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j

public class ConnectionConsumer {
    private  final NotificationService notificationService  ;
    @KafkaListener(topics = "connection_request_topic")
    void HandleConnectionReceivedRequest(ConnectionEvent connectionEvent)
    {

        log.info("handling the event => connection request received from UserId{}",connectionEvent.getSenderId());

        String message = String.format("your connection with id: %d has send connection request",
                 connectionEvent.getSenderId()) ;

        Notification notification = Notification.builder()
                .message(message)
                .userId( connectionEvent.getReceiverId())
                .build();

        notificationService.addNotification(notification);

    }


    @KafkaListener(topics = "accept_connection_request_topic")
    void HandleConnectionAccept(ConnectionEvent connectionEvent)
    {
        log.info("handling the event => connection request accepted by UserId{}",connectionEvent.getReceiverId());

        String message = String.format("your connection with id: %d has accept connection request",
                connectionEvent.getReceiverId()) ;

        Notification notification = Notification.builder()
                .message(message)
                .userId( connectionEvent.getSenderId())
                .build();

        notificationService.addNotification(notification);

    }

}
