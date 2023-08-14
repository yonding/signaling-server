package com.example.signalingserver.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;

@Getter
@Setter
@ToString
public class Room {
    private String roomName;
    private String password;
    private ArrayList<WebSocketSession> members;

    public boolean isFull(){
        return members.size() == 2;
    }

    public Room(String roomName, String password, WebSocketSession member){
        this.roomName = roomName;
        this.password = password;
        members = new ArrayList<>();
        this.members.add(member);
    }

    public void addMember(WebSocketSession member){
        members.add(member);
    }
    public void removeMember(WebSocketSession member){
        members.remove(member);
    }
}
