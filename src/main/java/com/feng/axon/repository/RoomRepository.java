package com.feng.axon.repository;

import com.feng.axon.model.ChatRoomId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, String> {
    Optional<Room> findByRoomId(ChatRoomId roomId);
}
