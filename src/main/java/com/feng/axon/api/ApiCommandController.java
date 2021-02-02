package com.feng.axon.api;

import com.feng.axon.command.*;
import com.feng.axon.config.command.MyCommandGateway;
import com.feng.axon.model.ChatRoomId;
import com.feng.axon.model.ChatterId;
import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.MetaData;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

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
    public Mono<Map<String, String>> createChatRoom( /** @Valid */@RequestBody CreateRoomCommand command) {
        ChatRoomId chatRoomId = ChatRoomId.newId();
        command.setRoomId(chatRoomId);
        Map<String, String> mapDate = new HashMap<>();
        mapDate.put("userId", "axonUser");
        commandGateway.send(command, MetaData.from(mapDate));
        return Mono.fromFuture(CompletableFuture.completedFuture(ImmutableMap.of("id", chatRoomId.toString())));
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
    public Mono<Map<String, Object>> joinChatRoom(@PathVariable ChatRoomId roomId,
                                                  @RequestBody JoinRoomCommand command) {
        Assert.isTrue(!StringUtils.isEmpty(command.getName()), "participant participant is null");
        command.setRoomId(roomId);
        MetaData metaData = MetaData.with("userId", "axonUser");
        return Mono.fromFuture(commandGateway.send(command, metaData)
                .thenApply(chatterId -> ImmutableMap.of("chatterId", chatterId)));
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
    public Mono<Void> postMessage(@PathVariable ChatRoomId roomId,
                                  @PathVariable ChatterId chatterId,
                                  @RequestBody PostMessageCommand command) {
        Assert.isTrue(!StringUtils.isEmpty(command.getMessage()), "post message is empty");
        command.setRoomId(roomId);
        command.setChatterId(chatterId);
        MetaData metaData = MetaData.with("userId", "axonUser");
        return Mono.fromFuture(commandGateway.send(command, metaData));
    }

    /**
     * 修改聊天者信息
     *
     * @param roomId
     * @param chatterId
     * @param command
     * @return
     */
    @PutMapping("/rooms/{roomId}/chatters/{chatterId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> updateChatterInfo(@PathVariable ChatRoomId roomId,
                                        @PathVariable ChatterId chatterId,
                                        @RequestBody UpdateChatterCommand command) {
        command.setRoomId(roomId);
        command.setChatterId(chatterId);
        MetaData metaData = MetaData.with("userId", "axonUser");
        return Mono.fromFuture(commandGateway.send(command, metaData));
    }

    /**
     * 离开聊天室
     *
     * @param roomId
     * @return
     */
    @DeleteMapping("/rooms/{roomId}/chatters/{chatterId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> leaveChatRoom(@PathVariable ChatRoomId roomId,
                                    @PathVariable ChatterId chatterId) {
        MetaData metaData = MetaData.with("userId", "axonUser");
        return Mono.fromFuture(commandGateway.send(new LeaveRoomCommand(roomId, chatterId), metaData));
    }

    /**
     * 删除聊天室
     * @param roomId
     * @return
     */
    @DeleteMapping("/room/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteRoom(@PathVariable ChatRoomId roomId) {
        MetaData metaData = MetaData.with("userId", "axonUser");
        return Mono.fromFuture(commandGateway.send(new DeleteRoomCommand(roomId), metaData));
    }

}
