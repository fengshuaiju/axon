package com.feng.axon.command;

import com.feng.axon.model.ChatRoomId;
import com.feng.axon.model.ChatterId;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LeaveRoomCommand extends Command {

    @NotNull
    private ChatterId chatterId;

    public LeaveRoomCommand(ChatRoomId chatRoomId, ChatterId chatterId){
        super(chatRoomId);
        this.chatterId = chatterId;
    }
}
