package com.example.postService.config;

import feign.form.spring.SpringFormEncoder;
import jakarta.persistence.Column;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper()
    {
        return  new ModelMapper() ;
    }

    @Bean
    public SpringFormEncoder feignFormEncoder() {
        return new SpringFormEncoder();
    }
}
