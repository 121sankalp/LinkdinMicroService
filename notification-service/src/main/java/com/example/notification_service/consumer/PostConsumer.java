package com.example.notification_service.consumer;

import com.example.notification_service.entity.Notification;
import com.example.notification_service.service.NotificationService;
import com.example.postService.event.PostCreatedEvent;
import com.example.postService.event.PostLikedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service

@RequiredArgsConstructor

@Slf4j

public class PostConsumer {

    private  final NotificationService notificationService  ;

    @KafkaListener(topics = "post_created_topic")
    public void handlePostCreatedEvent(PostCreatedEvent postCreatedEvent)
    {
        log.info("handling the event => post_created_event");

        log.info("received notification => post created");

        String message = String.format("your connection with id: %d has created post:%s",
                postCreatedEvent.getOwnerUserId(),postCreatedEvent.getContent()) ;

        Notification notification = Notification.builder()
                .message(message)
                .userId(postCreatedEvent.getUserId())
                .build();

         notificationService.addNotification(notification);

    }

    @KafkaListener(topics = "post_liked_topic")
    public void handlePostLiked(PostLikedEvent postLikedEvent)
    {
        log.info("handling the event => post_liked_event");

        log.info("received notification => post liked {}",postLikedEvent);

        String message = String.format("user with id :%d has like you post with id :%d",
                postLikedEvent.getLikedByUserId(),postLikedEvent.getPostId()) ;

        Notification notification  = Notification.builder()
                .message(message)
                .userId(postLikedEvent.getOwnerPostId())
                .build();

          notificationService.addNotification(notification);

    }


}
