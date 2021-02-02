package com.feng.axon.repository;

import com.feng.axon.event.*;
import com.feng.axon.query.AllRoomsQuery;
import com.feng.axon.query.RoomChatterQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoomProjection {

    private final RoomRepository roomRepository;
    private final QueryUpdateEmitter updateEmitter;
    private final ChatterRepository chatterRepository;

    @EventHandler
    @Transactional
    public void on(RoomCreated event, @Timestamp Instant createdAt) {
        roomRepository.save(new Room(event.getRoomId(), event.getName(), new HashSet<>(), createdAt));
    }

//    @EventHandler
//    @Transactional
//    public void on(ChatterJoined event) {
//        Chatter chatter = new Chatter(event.getChatterId(), event.getRoomId(), event.getSex(), event.getName());
//        roomRepository.findByRoomId(event.getRoomId()).ifPresent(room -> room.getChatters().add(event.getChatterId()));
//        chatterRepository.save(chatter);
//        //判断是在一个聊天室里的人员就发送消息更新人员页面
//        updateEmitter.emit(RoomChatterQuery.class, queryCondition -> {
//            System.err.println("========>>>>>>>>>>>>>" + event.getRoomId().equals(queryCondition.getRoomId()));
//            return true;
//        }, chatter);
//    }

    @EventHandler
    @Transactional
    public void on(RoomDeleted event){
        roomRepository.findByRoomId(event.getRoomId()).ifPresent(roomRepository::delete);
    }

    @EventHandler
    @Transactional
    public void on(ChatterLeaved event) {
        Room byRoomId = roomRepository.findByRoomId(event.getRoomId()).orElseThrow(RuntimeException::new);
        byRoomId.leaveRoom(event.getChatterId());
    }

    @QueryHandler
    public List<Room> on(AllRoomsQuery query) {
        return roomRepository.findAll();
    }

}
