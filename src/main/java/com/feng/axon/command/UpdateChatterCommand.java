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
public class UpdateChatterCommand {
    @NotNull
    @TargetAggregateIdentifier
    private ChatRoomId chatRoomId;

    @NotNull
//    @TargetAggregateIdentifier
    private ChatterId chatterId;

    @NotEmpty
    private String name;

    @NotNull
    private Sex sex;
}
