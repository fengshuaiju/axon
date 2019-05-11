package com.feng.axon.aggregate;

import com.feng.axon.command.UpdateChatterCommand;
import com.feng.axon.event.ChatterUpdated;
import com.feng.axon.model.ChatterId;
import com.feng.axon.model.Sex;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.EntityId;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Getter
//作为AggregateMember自身不能是Aggregate
//@Aggregate
@Accessors(fluent = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "chatterId")
public class Chatter {

    //@EntityId(routingKey = "customRoutingProperty") 如果command 中想自定义 寻找 EntityId 的名称
    //此处为自定义处 command 中的和此处定义的一致即可
    @EntityId
    private ChatterId chatterId;

    private String name;

    private Sex sex;

    @CommandHandler
    public void handle(UpdateChatterCommand command) {
        apply(new ChatterUpdated(command.getChatRoomId(), this.chatterId, command.getName(), command.getSex()));
    }

    @EventSourcingHandler
    public void on(ChatterUpdated event) {
        this.name = event.getName();
        this.sex = event.getSex();
    }
}
