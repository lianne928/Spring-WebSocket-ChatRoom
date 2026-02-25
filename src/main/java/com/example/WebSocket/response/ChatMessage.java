package com.example.WebSocket.response;

import lombok.Data;

@Data
public class ChatMessage {
    private String email, content, time;
}
