package com.feng.axon.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.feng.axon.model.ChatRoomId;
import com.feng.axon.model.ChatterId;
import com.feng.axon.repository.config.JpaConverterListJson;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.Instant;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class Room {
    @EmbeddedId
    private ChatRoomId roomId;

    private String name;

    private Instant createdAt;

    @JsonIgnore
    @Convert(converter = JpaConverterListJson.class)
    private Set<ChatterId> chatters;

    public Room(ChatRoomId roomId, String name, Set<ChatterId> chatters, Instant createdAt) {
        this.roomId = roomId;
        this.name = name;
        this.chatters = chatters;
        this.createdAt = createdAt;
    }

    public void leaveRoom(ChatterId id) {
        this.chatters.forEach(chatterId -> {
            if (chatterId.equals(id)) {
                chatters.remove(id);
            }
        });
    }
}
