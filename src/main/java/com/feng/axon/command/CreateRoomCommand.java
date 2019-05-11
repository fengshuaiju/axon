package com.feng.axon.command;

import com.feng.axon.model.ChatRoomId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoomCommand {

    @TargetAggregateIdentifier
    private ChatRoomId roomId;

    @NotEmpty
    private String name;
}

