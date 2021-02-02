package com.feng.axon.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.feng.axon.model.ChatRoomId;
import com.feng.axon.model.ChatterId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PostMessageCommand extends Command {

    @NotNull
    //此处名字必须和 AggregateMember 中 EntityId 的名字一样
    private ChatterId chatterId;

    @NotBlank
    private String message;

    @JsonCreator
    public PostMessageCommand(@JsonProperty(value = "message") String message) {
        this.message = message;
    }

    public PostMessageCommand(ChatRoomId chatRoomId, ChatterId chatterId, String message) {
        super(chatRoomId);
        this.chatterId = chatterId;
        this.message = message;
    }
}
