package com.feng.axon.aggregate;

import com.feng.axon.command.CreateRoomCommand;
import com.feng.axon.command.JoinRoomCommand;
import com.feng.axon.command.LeaveRoomCommand;
import com.feng.axon.command.PostMessageCommand;
import com.feng.axon.config.Person;
import com.feng.axon.event.MessagePostedEvent;
import com.feng.axon.event.ParticipantJoinedRoomEvent;
import com.feng.axon.event.ParticipantLeftRoomEvent;
import com.feng.axon.event.RoomCreatedEvent;
import com.feng.axon.model.ChatRoomId;
import com.feng.axon.model.ChatterId;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.MetaData;
import org.axonframework.messaging.annotation.MetaDataValue;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.modelling.command.ForwardMatchingInstances;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashSet;
import java.util.Set;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Slf4j
@Aggregate
@NoArgsConstructor
public class ChatRoom {

    @AggregateIdentifier
    private ChatRoomId roomId;
    private String name;

    @AggregateMember(eventForwardingMode = ForwardMatchingInstances.class)
    private Set<Chatter> chatters;

    /**
     * 创建聊天室
     *
     * @param command
     */
    @CommandHandler
    public ChatRoom(CreateRoomCommand command, MetaData metaData) {
        log.info(metaData.toString() + "@ " + metaData.get("key").toString());
        apply(new RoomCreatedEvent(command.getRoomId(), command.getName()));
    }

    /**
     * 加入聊天室
     *
     * @param command
     */
    @CommandHandler
    public ChatterId handle(JoinRoomCommand command, @MetaDataValue("metaData") Person metaData) {
        log.info(metaData.toString());
        ChatterId chatterId = ChatterId.newId();
        apply(new ParticipantJoinedRoomEvent(command.getRoomId(), chatterId, command.getParticipantName(), command.getSex()));
        return chatterId;
    }

    /**
     * 发送聊天消息
     *
     * @param command
     */
    @CommandHandler
    public void handle(PostMessageCommand command) {
        apply(new MessagePostedEvent(command.getRoomId(), command.getChatterId(), command.getMessage()));
    }

    /**
     * 离开聊天室
     *
     * @param command
     */
    @CommandHandler
    public void handle(LeaveRoomCommand command) {
        apply(new ParticipantLeftRoomEvent(command.getRoomId(), command.getChatterId()));
    }

    @EventSourcingHandler
    protected void on(RoomCreatedEvent event) {
        this.roomId = event.getRoomId();
        this.name = event.getName();
        this.chatters = new HashSet<>();
    }

    @EventSourcingHandler
    protected void on(ParticipantJoinedRoomEvent event) {
        chatters.add(new Chatter(event.getChatterId(), event.getName(), event.getSex()));
    }

    @EventSourcingHandler
    protected void on(ParticipantLeftRoomEvent event) {
        this.chatters.forEach(chatter -> {
            if (event.getChatterId().equals(chatter.chatterId())) {
                this.chatters.remove(chatter);
            }
        });
    }

}
