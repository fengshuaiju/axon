package com.feng.axon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomSummaryRepository extends JpaRepository<RoomSummary, String> {
    RoomSummary findByRoomId(String roomId);
}
