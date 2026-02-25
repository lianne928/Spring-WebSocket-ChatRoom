package com.example.WebSocket;


import com.example.WebSocket.util.JwtToken;
import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;


import java.security.Principal;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class StompConfig implements WebSocketMessageBrokerConfigurer{

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-chat")
                .addInterceptors(new HandshakeInterceptor() {
                    @Override
                    public boolean beforeHandshake(
                            ServerHttpRequest request,
                            ServerHttpResponse response,
                            WebSocketHandler wsHandler,
                            Map<String, Object> attributes) throws Exception {
                        if (request instanceof ServletServerHttpRequest req) {
                            HttpServletRequest httpreq = req.getServletRequest();

                            String tokenParam = httpreq.getParameter("token");
                            if (tokenParam != null && tokenParam.startsWith("Bearer ")) {
                                String token = tokenParam.substring(7);
                                if (token != null && JwtToken.isValid(token)) {
                                    String email = JwtToken.getEmail(token);
                                    attributes.put("email", email);
                                    return true;
                                }
                            }

                        }

                        return false;
                    }

                    @Override
                    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                               WebSocketHandler wsHandler, @Nullable Exception exception) {
                    }

                })
                .setAllowedOriginPatterns("*")
                .setHandshakeHandler(new DefaultHandshakeHandler() {

                    @Override
                    protected @Nullable Principal determineUser(
                            ServerHttpRequest request,
                            WebSocketHandler wsHandler,
                            Map<String, Object> attributes) {

                        String email = (String)attributes.get("email");
                        Principal principal = new Principal() {
                            @Override
                            public String getName() {
                                return email;
                            }
                        };

                        return principal;
                    }

                })
                .withSockJS();
    }


}