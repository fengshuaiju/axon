package com.feng.axon.command;

import com.feng.axon.model.ChatRoomId;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DeleteRoomCommand extends Command {

    public DeleteRoomCommand(ChatRoomId roomId) {
        super(roomId);
    }

}
