package com.feng.axon.event;

import com.feng.axon.model.ChatRoomId;
import com.feng.axon.model.ChatterId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessagePosted {
    private ChatRoomId roomId;
    private ChatterId chatterId;
    private String message;
}
