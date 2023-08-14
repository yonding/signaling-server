package com.example.signalingserver;

import com.example.signalingserver.model.Room;
import com.example.signalingserver.model.SignalData;
import com.example.signalingserver.model.SignalType;
import com.example.signalingserver.model.User;
import com.example.signalingserver.storage.RoomStorage;
import com.example.signalingserver.storage.UserStorage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SignalingHandler extends TextWebSocketHandler {

    @Autowired
    RoomStorage rooms;
    @Autowired
    UserStorage users;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("웹소켓이 연결되었습니다. 생성된 세션 아이디는 " + session.getId() + "입니다.");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        SignalData sigReq = objectMapper.readValue(message.getPayload(), SignalData.class);
        System.out.println("클라이언트로부터 메세지를 받았습니다. 메세지 내용 : " + sigReq.toString());

        String sigType = sigReq.getType();
        SignalData sigRes = new SignalData();

        if (sigType.equalsIgnoreCase(SignalType.Create.toString())) {
            sigRes.setType("Inform");
            if (rooms.get(sigReq.getRoomName()) == null) {
                rooms.put(sigReq.getRoomName(), new Room(sigReq.getRoomName(), sigReq.getPassword(), session));
                users.put(session.getId(), new User(session, sigReq.getRoomName()));
                System.out.println("방을 생성했습니다. 방 이름: " +sigReq.getRoomName());
                sigRes.setData("Created");
            } else {
                System.out.println("방 생성에 실패했습니다.");
                System.out.println("Failed");
                sigRes.setData("Failed");
            }
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(sigRes)));
        } else if (sigType.equalsIgnoreCase(SignalType.Join.toString())) {
            sigRes.setType("Inform");
            if (rooms.get(sigReq.getRoomName()) == null
                    || !rooms.get(sigReq.getRoomName()).getPassword().equals(sigReq.getPassword())
            ) {
                sigRes.setData("Wrong");
                System.out.println("방 이름 또는 비밀번호가 틀렸습니다.");
            } else if (rooms.get(sigReq.getRoomName()).isFull()) {
                sigRes.setData("Full");
                System.out.println("해당 방의 인원이 초과되었습니다.");
            } else {
                rooms.get(sigReq.getRoomName()).addMember(session);
                users.put(session.getId(), new User(session, sigReq.getRoomName()));
                System.out.println("방에 참여했습니다.");
                sigRes.setData("Joined");
                WebSocketSession peerSession = rooms.get(sigReq.getRoomName()).getMembers().get(0);
                peerSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(sigRes)));
            }
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(sigRes)));
        } else if (sigType.equalsIgnoreCase(SignalType.Answer.toString())) {
            System.out.println("Answer왔다.");
        } else if (sigType.equalsIgnoreCase(SignalType.Offer.toString())) {
            System.out.println("Offer왔다.");
        } else if (sigType.equalsIgnoreCase(SignalType.Ice.toString())) {
            System.out.println("Candidate왔다.");

        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("웹소켓 연결이 끊겼습니다. 끊긴 세션 아이디는 " + session.getId() + "입니다.");
        if(rooms.get(users.get(session.getId()).getRoomName()).isFull()){
            rooms.get(users.get(session.getId()).getRoomName()).removeMember(session);
            System.out.println("클라이언트가 방에서 나갔습니다. 방에 남아있는 사람이 있어 방은 사라지지 않았습니다.");
            System.out.println("---> "+rooms.toString());
        }else{
            rooms.remove(users.get(session.getId()).getRoomName());
            System.out.println("클라이언트가 방에서 나갔습니다. 방에 남아있는 사람이 없어 방이 사라졌습니다.");
            System.out.println("---> "+rooms.toString());
        }
        users.remove(session.getId());
        System.out.println("방을 나간 클라이언트의 정보를 삭제했습니다.");
        System.out.println("---> "+users.toString());
    }

}
