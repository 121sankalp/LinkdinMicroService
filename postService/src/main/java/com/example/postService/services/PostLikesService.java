package com.example.postService.services;

import com.example.postService.auth.AuthContextHolder;
import com.example.postService.entities.Post;
import com.example.postService.entities.PostLikes;
import com.example.postService.event.PostLikedEvent;
import com.example.postService.exceptions.BadRequestException;
import com.example.postService.exceptions.ResourceNotFoundException;
import com.example.postService.repositories.PostLikesRepository;
import com.example.postService.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostLikesService {

    private  final PostLikesRepository postLikesRepository ;
    private final PostRepository postRepository ;
    private final ModelMapper modelMapper ;
    private final  KafkaTemplate<Long , PostLikedEvent> postLikedEventKafkaTemplate ;

    @Transactional
    public void likePost(Long postId)  {
        Long userId = AuthContextHolder.getCurrentUserId();
        log.info("liking a post with postId {} " , postId);
       Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post not found with  id"+postId)) ;
        boolean hasAlreadyLiked = postLikesRepository.existsByUserIdAndPostId(userId , postId) ;
        if (hasAlreadyLiked)
        {
            throw new BadRequestException("post liked , cannot like again ") ;
        }
        PostLikes postLikes =  new PostLikes() ;
        postLikes.setPostId(postId);
        postLikes.setUserId(userId);
        postLikesRepository.save(postLikes) ;

        //send notification to owner of the post

            PostLikedEvent postLikedEvent = PostLikedEvent.builder()
                    .postId(postId)
                    .likedByUserId(userId)
                    .ownerPostId(post.getUserId())
                    .build();

        postLikedEventKafkaTemplate.send("post_liked_topic",postLikedEvent) ;


    }

    @Transactional
    public void unlikePost(Long postId) {
        Long userId = AuthContextHolder.getCurrentUserId() ;

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
