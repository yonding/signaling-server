package com.example.signalingserver.storage;

import com.example.signalingserver.model.Room;
import com.example.signalingserver.model.User;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserStorage {

    private ConcurrentHashMap<String, User> rooms = new ConcurrentHashMap<>();

    public void put(String sessionId, User user){
        rooms.put(sessionId, user);
    }

    public User get(String sessionId){
        return rooms.get(sessionId);
    }

    public void remove(String sessionId){
        rooms.remove(sessionId);
    }
}
