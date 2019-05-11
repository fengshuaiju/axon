package com.feng.axon.repository;

import com.feng.axon.model.ChatRoomId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findAllByRoomIdOrderByTimestamp(ChatRoomId roomId);
}
