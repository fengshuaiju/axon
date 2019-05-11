package com.feng.axon.command;

import com.feng.axon.model.ChatRoomId;
import com.feng.axon.model.ChatterId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRoomCommand {

    @TargetAggregateIdentifier
    private ChatRoomId roomId;

    @NotNull
    private ChatterId chatterId;
}
