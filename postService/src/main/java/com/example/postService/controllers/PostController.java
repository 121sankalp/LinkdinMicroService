package com.example.postService.controllers;

import com.example.postService.auth.AuthContextHolder;
import com.example.postService.client.ConnectionServiceClient;
import com.example.postService.dtos.PersonDto;
import com.example.postService.dtos.PostCreateRequestDto;
import com.example.postService.dtos.PostDto;
import com.example.postService.services.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
public class PostController {
    private final PostService postService  ;
    private final ConnectionServiceClient connectionServiceClient ;
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostDto> createPost(@RequestPart("post") PostCreateRequestDto postCreateRequestDto,
                                              @RequestPart("file") MultipartFile file) {
        PostDto postDto = postService.createPost(postCreateRequestDto, file);
        return new ResponseEntity<>(postDto, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    ResponseEntity<PostDto> getPostById(@PathVariable Long id)
    {
        PostDto postDto = postService.getPostById(id) ;
        Long userId= AuthContextHolder.getCurrentUserId()  ;
        // TODO: REMOVE IN FUTURE
        //call connection service from the post service and pass the user id inside the feign client
       List<PersonDto>postDtoList  = connectionServiceClient.getFirstDegreeConnection(  userId) ;
        return ResponseEntity.ok(postDto) ;
    }
    @GetMapping("/users/{userId}/allPosts")
    ResponseEntity<List<PostDto>> getAllPost(@PathVariable Long userId)
    {
        List<PostDto> postDtoList = postService.getAllPostOfUser(userId) ;
        return ResponseEntity.ok(postDtoList) ;
    }






}
