package com.feng.axon.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRoomCommand {

    @TargetAggregateIdentifier
    private String roomId;
    private String name;

}
