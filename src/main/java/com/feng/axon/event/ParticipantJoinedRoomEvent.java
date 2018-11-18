package com.feng.axon.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParticipantJoinedRoomEvent {
    private String roomId;
    private String participant;
}
