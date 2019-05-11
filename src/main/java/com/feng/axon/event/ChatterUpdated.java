package com.feng.axon.event;

import com.feng.axon.model.ChatRoomId;
import com.feng.axon.model.ChatterId;
import com.feng.axon.model.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatterUpdated {
    private ChatRoomId chatRoomId;
    private ChatterId chatterId;
    private String name;
    private Sex sex;
}
