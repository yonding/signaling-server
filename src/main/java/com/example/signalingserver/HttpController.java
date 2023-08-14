package com.example.signalingserver;

import com.example.signalingserver.model.Room;
import com.example.signalingserver.storage.RoomStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class HttpController {
    @Autowired
    RoomStorage rooms;

    @GetMapping("/creatable")
    public boolean isCreatable(String roomName) {
        if (rooms.get(roomName) == null) {
            System.out.println("클라이언트가 만들고자 하는 방의 이름이 중복되지 않습니다.");
            return true;
        }
        System.out.println("클라이언트가 만들고자하는 방의 이름이 이미 존재합니다.");
        return false;
    }

    @PostMapping("/joinable")
    public String isJoinable(@RequestBody Room room) {
        if (rooms.get(room.getRoomName()) == null) {
            System.out.println("클라이언트가 참여하고자하는 방이 존재하지 않습니다.");
            return "null";
        } else if (!rooms.get(room.getRoomName()).getPassword().equals(room.getPassword())) {
            System.out.println("클라이언트가 비밀번호가 틀렸습니다.");
            return "wrong";
        } else if (rooms.get(room.getRoomName()).isFull()) {
            System.out.println("클라이언트가 참여하고자 하는 방이 인원 초과입니다.");
            return "full";
        }
        return "true";
    }
}
