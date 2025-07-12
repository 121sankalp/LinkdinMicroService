package com.example.postService.services;

import com.example.postService.entities.Post;
import com.example.postService.entities.PostLikes;
import com.example.postService.exceptions.BadRequestException;
import com.example.postService.exceptions.ResourceNotFoundException;
import com.example.postService.repositories.PostLikesRepository;
import com.example.postService.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostLikesService {
    private  final PostLikesRepository postLikesRepository ;
    private final PostRepository postRepository ;
    private final ModelMapper modelMapper ;
    @Transactional
    public void likePost(Long postId)  {
        Long userId = 1L ;
        log.info("liking a post with postId {} " , postId);
       // Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post not found with  id"+postId)) ;
        boolean hasAlreadyLiked = postLikesRepository.existsByUserIdAndPostId(userId , postId) ;
        if (hasAlreadyLiked)
        {
            throw new BadRequestException("post liked , cannot like again ") ;
        }
        PostLikes postLikes =  new PostLikes() ;
        postLikes.setPostId(postId);
        postLikes.setUserId(userId);
        postLikesRepository.save(postLikes) ;

    }

    @Transactional
    public void unlikePost(Long postId) {
        Long userId = 1L ;
        log.info("unliking the post with post id {}",postId);
        // Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post not found with  id"+postId)) ;
        boolean hasAlreadyLiked = postLikesRepository.existsByUserIdAndPostId(userId , postId) ;
        if (!hasAlreadyLiked)
        {
            throw new BadRequestException("post is not liked , cannot unlike it ") ;
        }

        postLikesRepository.deleteByUserIdAndPostId(userId , postId) ;

    }
}
