package com.feng.axon.repository;

import com.feng.axon.event.ParticipantJoinedRoomEvent;
import com.feng.axon.event.ParticipantLeftRoomEvent;
import com.feng.axon.event.RoomCreatedEvent;
import com.feng.axon.query.AllRoomsQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoomSummaryProjection {

    private final RoomSummaryRepository roomSummaryRepository;

    @EventHandler
    public void on(RoomCreatedEvent event) {
        roomSummaryRepository.save(new RoomSummary(event.getRoomId(), event.getName(), new HashSet<>()));
    }

    @EventHandler
    public void on(ParticipantJoinedRoomEvent event){
        roomSummaryRepository.findByRoomId(event.getRoomId()).joinedParticipant(event.getParticipant());
    }

    @EventHandler
    @Transactional
    public void on(ParticipantLeftRoomEvent event){
        RoomSummary byRoomId = roomSummaryRepository.findByRoomId(event.getRoomId());
        byRoomId.leaveRoom(event.getParticipant());
    }

    @QueryHandler
    public List<RoomSummary> on(AllRoomsQuery query){
        return roomSummaryRepository.findAll();
    }

}
