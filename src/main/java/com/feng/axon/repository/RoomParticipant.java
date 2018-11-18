package com.feng.axon.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class RoomParticipant {

    @Id
    @GeneratedValue
    private Long id;

    private String roomId;
    private String participant;

    public RoomParticipant(String roomId, String participant) {
        this.roomId = roomId;
        this.participant = participant;
    }

}
