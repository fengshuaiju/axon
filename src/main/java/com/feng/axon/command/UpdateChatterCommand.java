package com.feng.axon.command;

import com.feng.axon.model.ChatRoomId;
import com.feng.axon.model.ChatterId;
import com.feng.axon.model.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UpdateChatterCommand extends Command {
    @NotNull
    private ChatterId chatterId;

    @NotEmpty
    private String name;

    @NotNull
    private Sex sex;

    public UpdateChatterCommand(ChatRoomId chatRoomId, ChatterId chatterId, String name, Sex sex) {
        super(chatRoomId);
        this.chatterId = chatterId;
        this.name = name;
        this.sex = sex;
    }
}
