package com.feng.axon.repository;

import com.feng.axon.model.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class RoomSummary {
    @EmbeddedId
    private ChatRoomId roomId;
    private String name;

    private Instant createdAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "room_participants", joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "room_id"))
    private Set<Participant> participants;

    public RoomSummary(ChatRoomId roomId, String name, Set<Participant> participants, Instant createdAt) {
        this.roomId = roomId;
        this.name = name;
        this.participants = participants;
        this.createdAt = createdAt;
    }

    public void joinedParticipant(ChatterId chatterId, Sex sex, String name) {
        if (CollectionUtils.isEmpty(participants)) {
            this.participants = new HashSet<>();
        }
        this.participants.add(new Participant(chatterId, sex, name));
    }

    public void leaveRoom(ChatterId chatterId) {
        this.participants.forEach(participant -> {
            if (participant.chatterId().equals(chatterId)) {
                participants.remove(participant);
            }
        });
    }

    @Transactional
    public void updateInfo(ChatterId chatterId, String name, Sex sex) {
        participants.forEach(participant -> {
            if (participant.chatterId().equals(chatterId)) {
                participant.name(name);
                participant.sex(sex);
            }
        });
    }
}
