package com.feng.axon.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostMessageCommand {
    @TargetAggregateIdentifier
    private String roomId;

    @NotEmpty
    private String message;

    @NotEmpty
    private String participant;
}
