package com.example.postService.event;

import lombok.Builder;
import lombok.Data;

@Data



public class PostLikedEvent {

    private  Long postId ;

    private  Long likedByUserId ;

    private  Long ownerPostId ;




}
