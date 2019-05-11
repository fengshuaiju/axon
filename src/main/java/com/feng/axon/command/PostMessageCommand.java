package com.feng.axon.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.feng.axon.model.ChatRoomId;
import com.feng.axon.model.ChatterId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostMessageCommand {
    @TargetAggregateIdentifier
    private ChatRoomId roomId;

    @NotNull
    //此处名字必须和 AggregateMember 中 EntityId 的名字一样
    private ChatterId chatterId;

    @NotBlank
    private String message;

    @JsonCreator
    public PostMessageCommand(@JsonProperty(value = "message") String message) {
        this.message = message;
    }
}
