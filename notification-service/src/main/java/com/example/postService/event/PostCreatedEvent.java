package com.example.postService.event;

import lombok.Builder;
import lombok.Data;

@Data

public class PostCreatedEvent {

    private  Long postId ;

    private  Long userId ;

    private  String content  ;

    private  Long ownerUserId ;


}
