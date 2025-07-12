package com.example.userService.controllers;

import com.example.userService.dtos.LoginRequestDto;
import com.example.userService.dtos.SignUpRequestDto;
import com.example.userService.dtos.UserDto;
import com.example.userService.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/auth")

@RequiredArgsConstructor

public class UserController {

    private  final AuthService authService ;
    @PostMapping("/signup")
    ResponseEntity<UserDto > signUp(@RequestBody SignUpRequestDto signUpRequestDto)
    {
        UserDto userDto =   authService.signUp(signUpRequestDto) ;
        return   new ResponseEntity<>(userDto,HttpStatus.CREATED ) ;
    }

    @PostMapping("/login")
    ResponseEntity<String > login(@RequestBody LoginRequestDto loginRequestDto)
    {
        String token =   authService.login(loginRequestDto) ;
        return    ResponseEntity.ok(token) ;
    }




}
