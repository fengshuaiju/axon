package com.feng.axon.event;

import com.feng.axon.model.ChatRoomId;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomDeleted {
    private ChatRoomId roomId;
}
