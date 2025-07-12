package com.example.postService.controllers;

import com.example.postService.services.PostLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/likes")
@RequiredArgsConstructor
@RestController
public class PostLikesController {
    private  final PostLikesService  postLikesService  ;

    @PostMapping("/{postId}")
    public ResponseEntity<Void> likePost(@PathVariable Long postId)
    {
        postLikesService.likePost(postId) ;
        return  ResponseEntity.noContent().build() ;
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> unlikePost(@PathVariable Long postId)
    {
        postLikesService.unlikePost(postId) ;
        return  ResponseEntity.noContent().build() ;
    }
}
