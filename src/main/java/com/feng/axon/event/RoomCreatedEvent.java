package com.feng.axon.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomCreatedEvent {
    private String roomId;
    private String name;
}
