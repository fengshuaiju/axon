package com.feng.axon.api;

import com.feng.axon.command.*;
import com.feng.axon.config.MetaDataStudent;
import com.feng.axon.config.MyCommandGateway;
import com.feng.axon.config.Person;
import com.feng.axon.model.ChatRoomId;
import com.feng.axon.model.ChatterId;
import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.MetaData;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@RestController
@RequiredArgsConstructor
public class ApiCommandController {

    private final MyCommandGateway commandGateway;

    /**
     * 创建聊天室
     *
     * @param command
     * @return
     */
    @PostMapping("/rooms")
    @ResponseStatus(HttpStatus.CREATED)
    public Future<Map<String, String>> createChatRoom(@RequestBody @Valid CreateRoomCommand command) {
        Assert.notNull(command.getName(), "participant is mandatory for a chatroom");
        ChatRoomId chatRoomId = ChatRoomId.newId();
        command.setRoomId(chatRoomId);
        Map<String, String> mapDate = new HashMap<>();
        mapDate.put("key", "val");
        commandGateway.send(command, MetaData.from(mapDate));
        return CompletableFuture.completedFuture(ImmutableMap.of("id", chatRoomId.toString()));
    }

    /**
     * 加入聊天室
     *
     * @param roomId
     * @param command
     * @return
     */
    @PutMapping("/rooms/{roomId}/participant")
    @ResponseStatus(HttpStatus.OK)
    public Future<Map<String, Object>> joinChatRoom(@PathVariable ChatRoomId roomId,
                                                    @RequestBody @Valid JoinRoomCommand command) {
        Assert.isTrue(!StringUtils.isEmpty(command.getParticipantName()), "participant participant is null");
        command.setRoomId(roomId);
        Person student = new MetaDataStudent(12, "feng");
        return commandGateway.send(command, student).thenApply(chatterId ->
                ImmutableMap.of("chatterId", chatterId));
    }

    /**
     * 在聊天室发送消息
     *
     * @param roomId
     * @param command
     * @return
     */
    @PutMapping("/rooms/{roomId}/messages/{chatterId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Future<Void> postMessage(@PathVariable ChatRoomId roomId,
                                    @PathVariable ChatterId chatterId,
                                    @RequestBody PostMessageCommand command) {
        Assert.isTrue(!StringUtils.isEmpty(command.getMessage()), "post message is empty");
        command.setRoomId(roomId);
        command.setChatterId(chatterId);
        return commandGateway.send(command);
    }

    /**
     * 修改聊天者信息
     *
     * @param roomId
     * @param chatterId
     * @param command
     * @return
     */
    @PutMapping("/rooms/{roomId}/messages/{chatterId}/info")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Future<Void> updateChatterInfo(@PathVariable ChatRoomId roomId,
                                          @PathVariable ChatterId chatterId,
                                          @RequestBody UpdateChatterCommand command) {
        //TODO 已生成的聊天记录里的聊天者姓名要不要改动
        command.setChatRoomId(roomId);
        command.setChatterId(chatterId);
        return commandGateway.send(command);
    }

    /**
     * 离开聊天室
     *
     * @param roomId
     * @return
     */
    @DeleteMapping("/rooms/{roomId}/participants/{chatterId}/leave")
    @ResponseStatus(HttpStatus.OK)
    public Future<Void> leaveChatRoom(@PathVariable ChatRoomId roomId,
                                      @PathVariable ChatterId chatterId) {
        return commandGateway.send(LeaveRoomCommand.builder()
                .roomId(roomId)
                .chatterId(chatterId)
                .build()
        );
    }

}
