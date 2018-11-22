package com.feng.axon.api;

import com.feng.axon.command.CreateRoomCommand;
import com.feng.axon.command.JoinRoomCommand;
import com.feng.axon.command.LeaveRoomCommand;
import com.feng.axon.command.PostMessageCommand;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;
import java.util.concurrent.Future;

@RestController
@RequiredArgsConstructor
public class ApiCommandController {

    private final CommandGateway commandGateway;

    /**
     * 创建聊天室
     * @param room
     * @return
     */
    @PostMapping("/rooms")
    @ResponseStatus(HttpStatus.CREATED)
    public Future<String> createChatRoom(@RequestBody @Valid Room room) {
        Assert.notNull(room.getName(), "participant is mandatory for a chatroom");
        String roomId = room.getRoomId() == null ? UUID.randomUUID().toString() : room.getRoomId();
        return commandGateway.send(new CreateRoomCommand(roomId, room.getName()));
    }

    /**
     * 加入聊天室
     * @param roomId
     * @param participant
     * @return
     */
    @PostMapping("/rooms/{roomId}/participants")
    @ResponseStatus(HttpStatus.CREATED)
    public Future<Void> joinChatRoom(@PathVariable String roomId, @RequestBody @Valid Participant participant) {
        Assert.isTrue(!StringUtils.isEmpty(participant.getName()),"participant participant is null");
        return commandGateway.send(new JoinRoomCommand(roomId, participant.getName()));
    }

    /**
     * 在聊天室发送消息
     * @param roomId
     * @param message
     * @return
     */
    @PostMapping("/rooms/{roomId}/messages")
    @ResponseStatus(HttpStatus.CREATED)
    public Future<Void> postMessage(@PathVariable String roomId, @RequestBody @Valid PostMessageRequest message) {
        boolean participantEmpty = StringUtils.isEmpty(message.getParticipant());
        Assert.isTrue(!participantEmpty, "post message is empty");
        return commandGateway.send(new PostMessageCommand(roomId, message.getMessage(), message.getParticipant()));
    }

    /**
     * 离开聊天室
     * @param roomId
     * @param participant
     * @return
     */
    @DeleteMapping("/rooms/{roomId}/participants")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Future<Void> leaveChatRoom(@PathVariable String roomId, @RequestBody @Valid Participant participant) {
        boolean nameEmpty = StringUtils.isEmpty(participant.getName());
        Assert.isTrue(!nameEmpty, "participant participant is empty");
        return commandGateway.send(new LeaveRoomCommand(roomId, participant.getName()));
    }

    public static class PostMessageRequest {

        @NotEmpty
        private String participant;
        @NotEmpty
        private String message;

        public String getParticipant() {
            return participant;
        }

        public void setParticipant(String participant) {
            this.participant = participant;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class Participant {

        @NotEmpty
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Room {

        private String roomId;
        @NotEmpty
        private String name;

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
