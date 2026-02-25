package com.example.WebSocket.handler;


import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashSet;
import java.util.Set;

public class MyWebSockethandler extends TextWebSocketHandler {

    //private static final Set<WebSocketSession> sessionSec = new CopyOnWriteArraySet<WebSocketSession>();
    private static final Set<WebSocketSession> sessions = new HashSet<>();

    private static boolean isExistTeacher = false;
    private static WebSocketSession teacherSession;

    public void MyWebSocketHandler() {
        System.out.println("MyWebSocketHandler");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("afterConnectionEstablished");
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String mesg = message.getPayload();
        // 判斷是否 Teacher => mesg
        if (!isExistTeacher && mesg.contains("isTeacher")) {
            isExistTeacher = true;
            teacherSession = session;
            System.out.println("Teacher Exist");
        }else if (session == teacherSession) {
            System.out.println("Teacher Drawing");
            for (WebSocketSession s : sessions) {
                if (s.isOpen()) {
                    s.sendMessage(message);
                }
            }
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

}
