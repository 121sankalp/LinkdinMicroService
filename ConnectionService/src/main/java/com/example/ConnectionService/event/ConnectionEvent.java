package com.example.ConnectionService.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class ConnectionEvent {
    private Long SenderId ;
    private Long receiverId ;
}
