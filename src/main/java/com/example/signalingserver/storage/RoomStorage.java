package com.example.signalingserver.storage;

import com.example.signalingserver.model.Room;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RoomStorage {
    private ConcurrentHashMap<String, Room> rooms = new ConcurrentHashMap<>();

    public void put(String roomName, Room room){
        rooms.put(roomName, room);
    }

    public Room get(String roomName){
        return rooms.get(roomName);
    }

    public void remove(String roomName){
        rooms.remove(roomName);
    }
}
