package com.feng.axon.repository;

import com.feng.axon.event.ParticipantJoinedRoomEvent;
import com.feng.axon.event.ParticipantLeftRoomEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RoomParticipantsProjection {

    private final RoomParticipantsRepository roomParticipantsRepository;

    @EventHandler
    @Transactional
    public void on(ParticipantJoinedRoomEvent event){
        RoomParticipant participant = new RoomParticipant(event.getRoomId(), event.getParticipant());
        roomParticipantsRepository.save(participant);
    }

    @EventHandler
    @Transactional
    public void on(ParticipantLeftRoomEvent event){
        roomParticipantsRepository.deleteByRoomIdAndParticipant(event.getRoomId(), event.getParticipant());
    }

}
