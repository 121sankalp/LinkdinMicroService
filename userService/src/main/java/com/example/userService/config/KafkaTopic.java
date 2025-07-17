package com.example.userService.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;

@Configuration

public class KafkaTopic {

  NewTopic UserCreatedTopic()
  {
      return  new NewTopic("user_created_topic",3,(short) 1) ;
  }

}
