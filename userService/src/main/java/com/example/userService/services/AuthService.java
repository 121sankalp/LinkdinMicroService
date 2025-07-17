package com.example.userService.services;

import com.example.userService.dtos.LoginRequestDto;
import com.example.userService.dtos.SignUpRequestDto;
import com.example.userService.dtos.UserDto;
import com.example.userService.entities.User;
import com.example.userService.event.UserCreatedEvent;
import com.example.userService.exceptions.BadRequestException;
import com.example.userService.exceptions.ResourceNotFoundException;
import com.example.userService.repositories.UserRepository;
import com.example.userService.utility.Bcrypt;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private  final ModelMapper modelMapper ;
    private  final UserRepository userRepository ;
    private final JWTService jwtService ;
    private final KafkaTemplate<Long, UserCreatedEvent> userCreatedEventKafkaTemplate ;
    public UserDto signUp(SignUpRequestDto signUpRequestDto) {
      log.info("signUp the user with the email {}" , signUpRequestDto.getEmail());

       boolean exists = userRepository.existsByEmail(signUpRequestDto.getEmail()) ;
       if(exists)
       {
           throw new BadRequestException("user already exists " + signUpRequestDto.getEmail());

       }

        User user = modelMapper.map(signUpRequestDto,User.class) ;

        user.setPassword(Bcrypt.hash(user.getPassword()));


         userRepository.save(user ) ;

        UserCreatedEvent userCreatedEvent = UserCreatedEvent.builder()
                .name(user.getName())
                .userId(user.getId())
                .build();

        userCreatedEventKafkaTemplate.send("user_created_topic",userCreatedEvent) ;


         return  modelMapper.map(user , UserDto.class) ;

    }


    public String login(LoginRequestDto loginRequestDto) {
        log.info("logging user with email {}", loginRequestDto.getEmail());

       User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(()->new ResourceNotFoundException("email/password dos not exists")) ;

       boolean isPasswordMatch = Bcrypt.match(loginRequestDto.getPassword() , user.getPassword()) ;

       if(isPasswordMatch)
       {
           return jwtService.generateAccessToken(user);
       }
       else {
           throw new BadRequestException("email/password is incorrect") ;
       }


    }
}
