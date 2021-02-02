package com.feng.axon.repository;

import com.feng.axon.event.*;
import com.feng.axon.model.Sex;
import com.feng.axon.query.RoomChatterQuery;
import com.feng.axon.query.RoomMessagesQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatMessageProjection {

    private final RoomRepository roomRepository;
    private final QueryUpdateEmitter updateEmitter;
    private final ChatterRepository chatterRepository;
    private final ChatMessageRepository messageRepository;

    @EventHandler
    @Transactional
    public void on(MessagePosted event) {
        roomRepository.findByRoomId(event.getRoomId()).orElseThrow(RuntimeException::new);
        Chatter chatter = chatterRepository.findByChatterId(event.getChatterId()).orElseThrow(RuntimeException::new);
        ChatMessage message = messageRepository.save(new ChatMessage(event.getRoomId(), event.getChatterId(), event.getMessage(), chatter.getName()));

        //发射信息，通知 RoomMessagesQuery 更新了
//        updateEmitter.emit(RoomMessagesQuery.class, query -> {
//                    System.err.println(query.toString()+"--->" + query.getRoomId().equals(event.getRoomId()));
//                    log.error(query.toString()+"--->" + query.getRoomId().equals(event.getRoomId()));
//                    return true;
//                },
//                message);
    }

    @QueryHandler
    public List<ChatMessage> on(RoomMessagesQuery query) {
        return messageRepository.findAllByRoomIdOrderByTimestamp(query.getRoomId());
    }

    //两个query不能放在一个类中
    @QueryHandler(queryName = "room")
    public List<Chatter> onSecondQuery(RoomChatterQuery query) {
        return Stream.of(new Chatter(null, null, Sex.MALE, "美国队长"),
                new Chatter(null, null, Sex.MALE, "灭霸队长"),
                new Chatter(null, null, Sex.MALE, "死侍队长"))
                .collect(Collectors.toList());
    }
}
