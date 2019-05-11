package com.feng.axon.command;

import com.feng.axon.model.ChatRoomId;
import com.feng.axon.model.ChatterId;
import com.feng.axon.model.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinRoomCommand {
    @TargetAggregateIdentifier
    private ChatRoomId roomId;

    @NotEmpty
    private String participantName;

    @NotNull
    private Sex sex;
}
