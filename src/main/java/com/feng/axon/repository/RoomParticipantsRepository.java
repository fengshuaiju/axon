package com.feng.axon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomParticipantsRepository extends JpaRepository<RoomParticipant, Long> {
    void deleteByRoomIdAndParticipant(String roomId, String participant);
}
