package com.feng.axon.aggregate;

import com.feng.axon.command.*;
import com.feng.axon.event.*;
import com.feng.axon.model.ChatRoomId;
import com.feng.axon.model.ChatterId;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.InterceptorChain;
import org.axonframework.messaging.MetaData;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.axonframework.messaging.interceptors.MessageHandlerInterceptor;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.modelling.command.CommandHandlerInterceptor;
import org.axonframework.modelling.command.ForwardMatchingInstances;
import org.axonframework.queryhandling.QueryMessage;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashSet;
import java.util.Set;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import static org.axonframework.modelling.command.AggregateLifecycle.markDeleted;

@Slf4j
@Aggregate
@NoArgsConstructor
public class ChatRoom {

    @AggregateIdentifier
    private ChatRoomId roomId;
    private String name;

    @AggregateMember(eventForwardingMode = ForwardMatchingInstances.class)
    private Set<Chatter> chatters;

    @SneakyThrows
    @CommandHandlerInterceptor
    public Object intercept(Command command, InterceptorChain interceptorChain) {
        if (this.roomId.equals(command.getRoomId())) {
            return interceptorChain.proceed();
        }
        return new IllegalArgumentException();
    }

    @MessageHandlerInterceptor
    public void intercept(QueryMessage<?, ?> queryMessage, InterceptorChain interceptorChain) throws Exception {
        //TODO Add your intercepting logic before
        interceptorChain.proceed();
        //TODO or after the InterceptorChain#proceed invocation
        System.err.println("QueryMessage===============>intercept");
    }

    @ExceptionHandler(resultType = IllegalArgumentException.class)
    public void handle(Exception exception) {
        //TODO How you prefer to react to this generic exception
        System.err.println("Exception===============>");
    }

    /**
     * 创建聊天室
     *
     * @param command
     */
    @CommandHandler
    public ChatRoom(CreateRoomCommand command, MetaData metaData) {
        apply(new RoomCreated(command.getRoomId(), command.getName()));
    }

    /**
     * 加入聊天室
     *
     * @param command
     */
    @CommandHandler
    public ChatterId handle(JoinRoomCommand command,
                            //@MetaDataValue("metaData") Person metaData
                            MetaData metaData) {
        ChatterId chatterId = ChatterId.newId();
        apply(new ChatterJoined(command.getRoomId(), chatterId, command.getName(), command.getSex()));
        return chatterId;
    }

    /**
     * 发送聊天消息
     *
     * @param command
     */
    @CommandHandler
    public void handle(PostMessageCommand command) {
        apply(new MessagePosted(command.getRoomId(), command.getChatterId(), command.getMessage()));
    }

    /**
     * 离开聊天室
     *
     * @param command
     */
    @CommandHandler
    public void handle(LeaveRoomCommand command) {
        apply(new ChatterLeaved(command.getRoomId(), command.getChatterId()));
    }

    /**
     * 删除聊天室
     * @param command
     */
    @CommandHandler
    public void handle(DeleteRoomCommand command){
        apply(new RoomDeleted(command.getRoomId()));
    }

    @EventSourcingHandler
    protected void on(RoomCreated event) {
        this.roomId = event.getRoomId();
        this.name = event.getName();
        this.chatters = new HashSet<>();
    }

    @EventSourcingHandler
    protected void on(ChatterJoined event) {
        chatters.add(new Chatter(event.getChatterId(), event.getName(), event.getSex()));
    }

    @EventSourcingHandler
    protected void on(ChatterLeaved event) {
        this.chatters.forEach(chatter -> {
            if (event.getChatterId().equals(chatter.chatterId())) {
                this.chatters.remove(chatter);
            }
        });
    }

    @EventSourcingHandler
    private void on(RoomDeleted event){
        //标记删除
        markDeleted();
    }

}
