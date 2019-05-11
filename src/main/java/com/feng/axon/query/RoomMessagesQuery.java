package com.feng.axon.query;

import com.feng.axon.model.ChatRoomId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomMessagesQuery {
    private ChatRoomId roomId;
}
