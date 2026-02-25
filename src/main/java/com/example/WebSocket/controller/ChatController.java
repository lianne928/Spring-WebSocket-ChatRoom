package com.example.WebSocket.controller;


import com.example.WebSocket.response.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class ChatController {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

    @MessageMapping("/chat/send")
    @SendTo("/topic/public")
    public ChatMessage handleMessage(String content, Principal principal) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setEmail(principal.getName());
        chatMessage.setContent(content);
        chatMessage.setTime(LocalDateTime.now().format(dtf));
        return chatMessage;
    }

}