package com.feng.axon.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
public class ChatRoomId implements Serializable {

    @Column(name = "room_id", columnDefinition = "uuid")
    private UUID value;

    @JsonValue
    @Override
    public String toString(){
        return value.toString();
    }

    public ChatRoomId(String value){
        this.value = UUID.fromString(value);
    }

    public ChatRoomId(UUID value){
        this.value = value;
    }

    public static ChatRoomId newId(){
        return new ChatRoomId(UUID.randomUUID());
    }

}
