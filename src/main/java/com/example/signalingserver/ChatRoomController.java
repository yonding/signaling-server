//package com.example.signalingserver;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.event.EventListener;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.annotation.SubscribeMapping;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.socket.messaging.SessionConnectEvent;
//import org.springframework.web.socket.messaging.SessionDisconnectEvent;
//
//import java.util.*;
//
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//public class ChatRoomController {
//
//    // 화상 회의 방 저장소
//    private final HashMap<String, ChatRoom> chatRooms;
//    private final HashMap<String, String> sessions;
//
//    // 방 생성
//    @MessageMapping("/createRoom")
//    public void createRoom(Map<String, String> roomInfo) {
//        String roomName = roomInfo.get("roomName");
//        String password = roomInfo.get("password");
//        String userId = roomInfo.get("userId");
//
//        sessions.put(userId, roomName);
//        chatRooms.put(roomName, new ChatRoom(roomName, password));
//    }
//
//    @MessageMapping("/joinRoom")
//    public void joinRoom(Map<String, String> roomInfo) {
//        String roomName = roomInfo.get("roomName");
//        String userId = roomInfo.get("userId");
//
//        sessions.put(userId, roomName);
//        chatRooms.get(roomName).setFull(true);
//    }
//    @CrossOrigin(origins = "*")
//    @PostMapping("/creatable")
//    public String isCreatable(@RequestBody Map<String, String> roomInfo) {
//        String roomName = roomInfo.get("roomName");
//
//        System.out.println("roomName = " + roomName);
//        System.out.println("chatRooms.get(roomName) = " + chatRooms.get(roomName));
//
//        if(chatRooms.get(roomName) == null)     return "creatable";
//        else                                    return "already";
//    }
//
//    @CrossOrigin(origins = "*")
//    @PostMapping("/joinable")
//    public String isJoinable(@RequestBody Map<String, String> roomInfo) {
//        String roomName = roomInfo.get("roomName");
//        String password = roomInfo.get("password");
//
//        if(chatRooms.get(roomName) == null)                              return "null";
//        else if(chatRooms.get(roomName).isFull())                        return "full";
//        else if(!chatRooms.get(roomName).getPassword().equals(password)) return "wrong";
//        else                                                             return "true";
//    }
//
//    @CrossOrigin(origins = "*")
//    @PostMapping("/close")
//    public void close(@RequestBody Map<String, String> userInfo) {
//        String userId = userInfo.get("userId");
//
//        if (!chatRooms.get(sessions.get(userId)).isFull()) {
//            chatRooms.remove(sessions.get(userId));
//        } else {
//            chatRooms.get(sessions.get(userId)).setFull(false);
//        }
//        System.out.println(userId+" is removed in sessions.");
//        sessions.remove(userId);
//    }
//
//
//
//    // 웹소켓 연결 성공 시 처리할 코드
//    @EventListener
//    public void handleWebSocketConnectListener(SessionConnectEvent event) {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//        String sessionId = headerAccessor.getSessionId();
//
//        System.out.println("1. WebSocket connection established: " + sessionId);
//    }
//
//    // 웹소켓 연결 해제 시 처리할 코드
//    @EventListener
//    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//        String sessionId = headerAccessor.getSessionId();
//        System.out.println("WebSocket connection disconnected: " + sessionId);
//    }
//
//    @SubscribeMapping("/room/{roomName}")
//    public String subscribeRoom(@Payload String roomName) {
//        return roomName;
//    }
//
//    @MessageMapping("/sendMessage/{roomName}") // 클라이언트가 '/app/chat.sendMessage'로 메시지를 보낼 때 호출
//    @SendTo("/room/{roomName}") // 메시지를 '/topic/publicChatRoom' 주제로 전송
//    public String sendMessage(@Payload String c) {
//        return c;
//    }
//
//}