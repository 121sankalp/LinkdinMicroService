package com.example.ConnectionService.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    NewTopic connectionRequestSent()
    {
        return  new NewTopic("connection_request_topic",3,(short)1) ;
    }

    NewTopic acceptConnectionRequestSent()
    {
        return  new NewTopic("accept_connection_request_topic",3,(short)1) ;
    }
}
