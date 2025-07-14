package com.example.ConnectionService.service;

import com.example.ConnectionService.dtos.PersonDto;
import com.example.ConnectionService.entity.Person;
import com.example.ConnectionService.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionService {
    private  final  PersonRepository personRepository ;
    public  List<Person> getFirstDegreeConnectionOfUser(Long userId)
    {
        log.info("getting first degree connection of user with userId {}" , userId);

        List<Person> personList = personRepository.getFirstDegreeConnections(userId) ;


        return  personList ;

    }

}
