package com.feng.axon.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessagePostedEvent {
    private String roomId;
    private String message;
    private String participant;
}
