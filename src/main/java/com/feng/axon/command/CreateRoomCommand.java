package com.feng.axon.command;

import com.feng.axon.model.ChatRoomId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateRoomCommand extends Command {

    @NotEmpty
    private String name;

    CreateRoomCommand(ChatRoomId chatRoomId, String name){
        super(chatRoomId);
        this.name = name;
    }
}

