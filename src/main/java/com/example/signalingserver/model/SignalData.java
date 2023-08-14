package com.example.signalingserver.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class SignalData {
    private String type;
    private String roomName;
    private String password;
    private String data;
}
