package com.feng.axon.repository;

import com.feng.axon.utils.SetToStringConverter;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class RoomSummary {

    @Id
    private String roomId;
    private String name;

    @Convert(converter = SetToStringConverter.class)
    private Set<String> participants;

    public RoomSummary(String roomId, String name, Set<String> participants) {
        this.roomId = roomId;
        this.name = name;
        this.participants = participants;
    }

    public void joinedParticipant(String participant) {
        if(CollectionUtils.isEmpty(participants)){
            this.participants = new HashSet<>();
            this.participants.add(participant);
        }else {
            this.participants.add(participant);
        }
    }

    public void leaveRoom(String participant) {
        this.participants.remove(participant);
    }
}
