package com.feng.axon.event;

import com.feng.axon.model.ChatRoomId;
import com.feng.axon.model.ChatterId;
import com.feng.axon.model.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatterJoined {
    private ChatRoomId roomId;
    private ChatterId chatterId;
    private String name;
    private Sex sex;
}
