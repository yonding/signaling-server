package com.example.signalingserver.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.socket.WebSocketSession;

@Getter
@Setter
@ToString

public class User {
    WebSocketSession session;
    String roomName;

    public User(WebSocketSession session, String roomName) {
        this.session = session;
        this.roomName = roomName;
    }

}
