package com.feng.axon.repository;

import com.feng.axon.event.ChatterJoined;
import com.feng.axon.event.ChatterLeaved;
import com.feng.axon.event.ChatterUpdated;
import com.feng.axon.query.RoomChatterQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatterProjection {

    private final RoomRepository roomRepository;
    private final QueryUpdateEmitter updateEmitter;
    private final ChatterRepository chatterRepository;

    @EventHandler
    @Transactional
    public void on(ChatterJoined event) {
        Chatter chatter = new Chatter(event.getChatterId(), event.getRoomId(), event.getSex(), event.getName());
        roomRepository.findByRoomId(event.getRoomId()).ifPresent(room -> room.getChatters().add(null));
        chatterRepository.save(chatter);

        //判断是在一个聊天室里的人员就发送消息更新人员页面
        updateEmitter.emit(RoomChatterQuery.class, queryCondition -> {
            System.err.println("========>>>>>>>>>>>>>" + event.getRoomId().equals(queryCondition.getRoomId()));
            return true;
        }, chatter);
    }

    @EventHandler
    @Transactional
    public void on(ChatterUpdated event) {
        Chatter chatter = chatterRepository.findByChatterId(event.getChatterId()).orElseThrow(RuntimeException::new);
        chatter.update(event.getName(), event.getSex());
    }

    @EventHandler
    @Transactional
    public void on(ChatterLeaved event) {
        chatterRepository.findByChatterId(event.getChatterId()).ifPresent(chatterRepository::delete);
    }

    @QueryHandler
    public List<Chatter> handel(RoomChatterQuery query) {
        return chatterRepository.findByChatRoomId(query.getRoomId());
    }

    //两个query不能放在一个类中
    @QueryHandler(queryName = "room")
    public List<Chatter> onFirstQuery(RoomChatterQuery query) {
        return chatterRepository.findByChatRoomId(query.getRoomId());
    }

}
