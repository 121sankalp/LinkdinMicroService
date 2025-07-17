package com.example.ConnectionService.consumer;

import com.example.ConnectionService.entity.Person;
import com.example.ConnectionService.service.PersonService;
import com.example.userService.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service

@RequiredArgsConstructor

@Slf4j

public class UserServiceConsumer {

    private  final PersonService personService  ;
    @KafkaListener(topics = "user_created_topic")
    public  void handlePersonCreated(UserCreatedEvent userCreatedEvent)
    {
        log.info("creating new node in neo4j db with user id {}",userCreatedEvent.getUserId());

        personService.createPerson(userCreatedEvent.getUserId(), userCreatedEvent.getName());

    }

}
