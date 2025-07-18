package com.example.ConnectionService.service;

import com.example.ConnectionService.auth.AuthContextHolder;
import com.example.ConnectionService.dtos.PersonDto;
import com.example.ConnectionService.entity.Person;
import com.example.ConnectionService.event.ConnectionEvent;
import com.example.ConnectionService.exceptions.BadRequestException;
import com.example.ConnectionService.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionService {
    private  final  PersonRepository personRepository ;
    private  final KafkaTemplate<Long , ConnectionEvent> connectionEventKafkaTemplate ;
    public  List<Person> getFirstDegreeConnectionOfUser(Long userId)
    {
        log.info("getting first degree connection of user with userId {}" , userId);

        List<Person> personList = personRepository.getFirstDegreeConnections(userId) ;


        return  personList ;

    }

    public void sendConnectionRequest(Long receiverId) {

        Long senderId = AuthContextHolder.getCurrentUserId() ;
        log.info("sending connection request with senderId:{},receiverId:{}",senderId,receiverId);
        if(senderId.equals(receiverId))
        {
            throw new BadRequestException("Both Sender and receiver are same") ;

        }

        boolean alreadySentRequest =  personRepository.connectionRequestExists(senderId,receiverId) ;

        if (alreadySentRequest)
        {
            throw new BadRequestException("Connection Request Already Exists cannot send again") ;
        }

        boolean alreadyConnected = personRepository.alreadyConnected(senderId,receiverId);

        if(alreadyConnected)
        {
            throw new BadRequestException("Already Connected , cannot send request again") ;
        }



        personRepository.addConnectionRequest(senderId, receiverId);

        ConnectionEvent connectionEvent = ConnectionEvent.builder()
                .SenderId(senderId)
                .receiverId(receiverId)
                .build();

        connectionEventKafkaTemplate.send("connection_request_topic",connectionEvent) ;

        log.info("successfully send the connection request ");

    }

    public  void  acceptConnectionRequest(Long senderId)
    {
        Long receiverId = AuthContextHolder.getCurrentUserId() ;
        log.info("Accepting connection request with senderId:{},receiverId:{}",senderId,receiverId);
        if(senderId.equals(receiverId))
        {
            throw new BadRequestException("Both Sender and receiver are same") ;

        }

        boolean alreadyConnected = personRepository.alreadyConnected(senderId,receiverId);

        if(alreadyConnected)
        {
            throw new BadRequestException("Already Connected , cannot accept connection request again") ;
        }

        boolean alreadySentRequest =  personRepository.connectionRequestExists(senderId,receiverId) ;
        if (!alreadySentRequest)
        {
            throw new BadRequestException(" No Connection Exists cannot accept without accept") ;
        }


        personRepository.acceptConnectionRequest(senderId,receiverId);

        ConnectionEvent connectionEvent = ConnectionEvent.builder()
                .SenderId(senderId)
                .receiverId(receiverId)
                .build();

        connectionEventKafkaTemplate.send("connection_request_topic",connectionEvent) ;



        log.info("successfully accepted the connection request withs senderId:{} , receiverId:{}",senderId,receiverId );

    }


    public void  rejectConnectionRequest(Long senderId)
    {
        Long receiverId = AuthContextHolder.getCurrentUserId() ;

        if(senderId.equals(receiverId))
        {
            throw new BadRequestException("Both Sender and receiver are same") ;
        }
        boolean alreadySentRequest =  personRepository.connectionRequestExists(senderId,receiverId) ;
        if (!alreadySentRequest)
        {
            throw new BadRequestException(" No Connection Exists cannot reject") ;
        }

        personRepository.rejectConnectionRequest(senderId,receiverId);

        log.info("successfully rejected the connection request withs senderId:{} , receiverId:{}",senderId,receiverId );

    }







}
