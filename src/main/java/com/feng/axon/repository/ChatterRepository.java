package com.feng.axon.repository;

import com.feng.axon.model.ChatRoomId;
import com.feng.axon.model.ChatterId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatterRepository extends JpaRepository<Chatter, String> {
    Optional<Chatter> findByChatterId(ChatterId chatterId);

    List<Chatter> findByChatRoomId(ChatRoomId roomId);
}
