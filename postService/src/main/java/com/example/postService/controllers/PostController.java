package com.example.postService.controllers;

import com.example.postService.dtos.PostCreateRequestDto;
import com.example.postService.dtos.PostDto;
import com.example.postService.services.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
public class PostController {
    private final PostService postService  ;
    @PostMapping
    ResponseEntity<PostDto> createPost(@RequestBody PostCreateRequestDto postCreateRequestDto)
    {
      PostDto postDto =   postService.createPost(postCreateRequestDto,1L) ;
      return new ResponseEntity<>(postDto, HttpStatus.CREATED) ;
    }
    @GetMapping("/{id}")
    ResponseEntity<PostDto> getPostById(@PathVariable Long id)
    {
        PostDto postDto = postService.getPostById(id) ;
        return ResponseEntity.ok(postDto) ;
    }
    @GetMapping("/users/{userId}/allPosts")
    ResponseEntity<List<PostDto>> getAllPost(@PathVariable Long userId)
    {
        List<PostDto> postDtoList = postService.getAllPostOfUser(userId) ;
        return ResponseEntity.ok(postDtoList) ;
    }






}
