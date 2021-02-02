package com.feng.axon.event;

import com.feng.axon.model.ChatRoomId;
import com.feng.axon.model.ChatterId;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatterLeaved {
    private ChatRoomId roomId;
    private ChatterId chatterId;
}
