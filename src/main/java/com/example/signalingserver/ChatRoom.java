package com.example.signalingserver;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class ChatRoom {
    private String roomId;
    private String roomName;
    private String password;
    private boolean isFull;

    public ChatRoom(String roomName, String password) {
        this.roomId = UUID.randomUUID().toString();
        this.roomName = roomName;
        this.password = password;
        this.isFull = false;
    }
}
