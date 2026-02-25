package com.example.WebSocket.websocket;

import com.example.WebSocket.handler.MyWebSockethandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        MyWebSockethandler handler = new MyWebSockethandler();
        registry.addHandler(handler, new String[] {"/ws"}).setAllowedOrigins("*");
    }

}