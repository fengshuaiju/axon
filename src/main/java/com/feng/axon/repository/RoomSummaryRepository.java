package com.feng.axon.repository;

import com.feng.axon.model.ChatRoomId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomSummaryRepository extends JpaRepository<RoomSummary, String> {
    Optional<RoomSummary> findByRoomId(ChatRoomId roomId);
}
