package com.feng.axon.api;

import com.feng.axon.command.CreateRoomCommand;
import com.feng.axon.command.JoinRoomCommand;
import com.feng.axon.command.LeaveRoomCommand;
import com.feng.axon.command.PostMessageCommand;
import com.feng.axon.config.MyCommandGateway;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;
import java.util.concurrent.Future;

@RestController
@RequiredArgsConstructor
public class ApiCommandController {

    private final CommandGateway commandGateway;

    private final MyCommandGateway myCommandGateway;

    /**
     * 创建聊天室
     * @param command
     * @return
     */
    @PostMapping("/rooms")
    @ResponseStatus(HttpStatus.CREATED)
    public Future<String> createChatRoom(@RequestBody @Valid CreateRoomCommand command) {
        Assert.notNull(command.getName(), "participant is mandatory for a chatroom");
        String roomId = command.getRoomId() == null ? UUID.randomUUID().toString() : command.getRoomId();
        command.setRoomId(roomId);
        return commandGateway.send(command);
//        return myCommandGateway.send(command, );
    }

    /**
     * 加入聊天室
     * @param roomId
     * @param command
     * @return
     */
    @PostMapping("/rooms/{roomId}/participants")
    @ResponseStatus(HttpStatus.CREATED)
    public Future<Void> joinChatRoom(@PathVariable String roomId, @RequestBody @Valid JoinRoomCommand command) {
        Assert.isTrue(!StringUtils.isEmpty(command.getParticipant()),"participant participant is null");
        command.setRoomId(roomId);
        return commandGateway.send(command);
    }

    /**
     * 在聊天室发送消息
     * @param roomId
     * @param command
     * @return
     */
    @PostMapping("/rooms/{roomId}/messages")
    @ResponseStatus(HttpStatus.CREATED)
    public Future<Void> postMessage(@PathVariable String roomId, @RequestBody @Valid PostMessageCommand command) {
        Assert.isTrue(!StringUtils.isEmpty(command.getParticipant()), "post message is empty");
        command.setRoomId(roomId);
        return commandGateway.send(command);
    }

    /**
     * 离开聊天室
     * @param roomId
     * @param command
     * @return
     */
    @DeleteMapping("/rooms/{roomId}/participants")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Future<Void> leaveChatRoom(@PathVariable String roomId, @RequestBody @Valid LeaveRoomCommand command) {
        Assert.isTrue(!StringUtils.isEmpty(command.getName()), "participant participant is empty");
        command.setRoomId(roomId);
        return commandGateway.send(command);
    }

}
