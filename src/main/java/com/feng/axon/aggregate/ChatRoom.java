package com.feng.axon.aggregate;

import com.feng.axon.command.CreateRoomCommand;
import com.feng.axon.command.JoinRoomCommand;
import com.feng.axon.command.LeaveRoomCommand;
import com.feng.axon.command.PostMessageCommand;
import com.feng.axon.event.MessagePostedEvent;
import com.feng.axon.event.ParticipantJoinedRoomEvent;
import com.feng.axon.event.ParticipantLeftRoomEvent;
import com.feng.axon.event.RoomCreatedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashSet;
import java.util.Set;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Slf4j
@Aggregate
@NoArgsConstructor
public class ChatRoom {

    @AggregateIdentifier
    private String roomId;
    private String name;
    private Set<String> participants;

    /**
     * 创建聊天室
     * @param command
     */
    @CommandHandler
    public ChatRoom(CreateRoomCommand command) {
        apply(new RoomCreatedEvent(command.getRoomId(), command.getName()));
    }

    /**
     * 加入聊天室
     * @param joinRoomCommand
     */
    @CommandHandler
    public void handle(JoinRoomCommand joinRoomCommand) {
        if (!participants.contains(joinRoomCommand.getParticipant())) {
            apply(new ParticipantJoinedRoomEvent(joinRoomCommand.getRoomId(), joinRoomCommand.getParticipant()));
        }
    }

    /**
     * 发送聊天消息
     * @param command
     */
    @CommandHandler
    public void handle(PostMessageCommand command){
        apply(new MessagePostedEvent(command.getRoomId(), command.getMessage(), command.getParticipant()));
    }

    /**
     * 离开聊天室
     * @param command
     */
    @CommandHandler
    public void handle(LeaveRoomCommand command){
        apply(new ParticipantLeftRoomEvent(command.getRoomId(), command.getName()));
    }


    @EventSourcingHandler
    protected void on(RoomCreatedEvent event) {
        this.roomId = event.getRoomId();
        this.name = event.getName();
        this.participants = new HashSet<>();
    }

    @EventSourcingHandler
    protected void on(ParticipantJoinedRoomEvent event) {
        participants.add(event.getParticipant());
    }

    @EventSourcingHandler
    protected void on(ParticipantLeftRoomEvent event){
        this.participants.remove(event.getParticipant());
    }

}