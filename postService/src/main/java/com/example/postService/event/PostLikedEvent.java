package com.example.postService.event;

import lombok.Builder;
import lombok.Data;

@Data

@Builder

public class PostLikedEvent {

    private  Long postId ;

    private  Long likedByUserId ;

    private  Long ownerPostId ;




}
