package com.feng.axon.repository;

import com.feng.axon.model.ChatRoomId;
import com.feng.axon.model.ChatterId;
import com.feng.axon.model.Sex;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Chatter {

    @Id
    @GeneratedValue
    private ChatterId chatterId;

    private ChatRoomId chatRoomId;

    @Enumerated(value = EnumType.STRING)
    private Sex sex;

    private String name;

    public Chatter(ChatterId chatterId, ChatRoomId chatRoomId, Sex sex, String name) {
        this.chatterId = chatterId;
        this.chatRoomId = chatRoomId;
        this.sex = sex;
        this.name = name;
    }

    public void update(String name, Sex sex) {
        this.name = name;
        this.sex = sex;
    }

}
