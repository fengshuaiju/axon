package com.feng.axon.repository;

import com.feng.axon.model.ChatRoomId;
import com.feng.axon.model.ChatterId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private ChatRoomId roomId;
    private ChatterId chatterId;
    private String message;
    @Setter
    private String chatterName;

    public ChatMessage(ChatRoomId roomId, ChatterId chatterId, String message){
        this.roomId = roomId;
        this.chatterId = chatterId;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

}
