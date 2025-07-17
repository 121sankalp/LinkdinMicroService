package com.example.ConnectionService.service;

import com.example.ConnectionService.entity.Person;
import com.example.ConnectionService.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository ;

    public void createPerson(Long userId , String name)
    {
        log.info("saving user in neo4j db ");


        Person person = Person.builder()
                .userId(userId)
                .name(name)
                .build();

        personRepository.save(person) ;


    }

}
