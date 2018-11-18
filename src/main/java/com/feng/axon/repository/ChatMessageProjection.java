package com.feng.axon.repository;

import com.feng.axon.event.MessagePostedEvent;
import com.feng.axon.query.RoomMessagesQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatMessageProjection {

    private final ChatMessageRepository repository;
    private final QueryUpdateEmitter updateEmitter;

    @EventHandler
    public void on(MessagePostedEvent event) {
        log.info("MessagePostedEvent=======");
        repository.save(new ChatMessage(event.getRoomId(), event.getMessage(), event.getParticipant()));
    }

    @QueryHandler
    public List<ChatMessage> on(RoomMessagesQuery query) {
        return repository.findAllByRoomIdOrderByTimestamp(query.getRoomId());
    }
}
