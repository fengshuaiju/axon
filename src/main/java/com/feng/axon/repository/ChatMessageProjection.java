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
    private final RoomSummaryRepository roomSummaryRepository;

    @EventHandler
    public void on(MessagePostedEvent event) {
        ChatMessage chatMessage = new ChatMessage(event.getRoomId(), event.getChatterId(), event.getMessage());
        RoomSummary roomSummary = roomSummaryRepository.findByRoomId(event.getRoomId()).orElseThrow(RuntimeException::new);
        roomSummary.getParticipants().forEach(chatter -> {
            if (event.getChatterId().equals(chatter.chatterId())) {
                chatMessage.setChatterName(chatter.name());
            }
        });

        repository.save(chatMessage);
        //发射信息，通知 Query 数据库更新了
        updateEmitter.emit(RoomMessagesQuery.class, query -> query.getRoomId().equals(event.getRoomId()), chatMessage);
    }

    @QueryHandler
    public List<ChatMessage> on(RoomMessagesQuery query) {
        return repository.findAllByRoomIdOrderByTimestamp(query.getRoomId());
    }
}
