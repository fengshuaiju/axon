package com.feng.axon.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue
    private Long id;

    private long timestamp;
    private String roomId;
    private String message;
    private String participant;

    public ChatMessage(String roomId, String message, String participant){
        this.roomId = roomId;
        this.message = message;
        this.participant = participant;
        this.timestamp = System.currentTimeMillis();
    }

}
