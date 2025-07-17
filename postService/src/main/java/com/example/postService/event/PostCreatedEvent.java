package com.example.postService.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostCreatedEvent {

    private  Long postId ;

    private  Long userId ;

    private  String content  ;

    private  Long ownerUserId ;


}
