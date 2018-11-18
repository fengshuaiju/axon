package com.feng.axon.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostMessageCommand {
    @TargetAggregateIdentifier
    private String roomId;
    private String message;
    private String participant;
}
