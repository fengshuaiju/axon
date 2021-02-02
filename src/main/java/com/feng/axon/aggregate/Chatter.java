package com.feng.axon.aggregate;

import com.feng.axon.command.LeaveRoomCommand;
import com.feng.axon.command.UpdateChatterCommand;
import com.feng.axon.event.ChatterUpdated;
import com.feng.axon.event.ChatterLeaved;
import com.feng.axon.model.ChatterId;
import com.feng.axon.model.Sex;
import lombok.*;
import lombok.experimental.Accessors;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.EntityId;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Getter
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
        apply(new ChatterUpdated(command.getRoomId(), this.chatterId, command.getName(), command.getSex()));
    }

    @CommandHandler
    public void handle(LeaveRoomCommand command) {
        apply(new ChatterLeaved(command.getRoomId(), command.getChatterId()));
    }

    @EventSourcingHandler
    public void on(ChatterUpdated event) {
        this.name = event.getName();
        this.sex = event.getSex();
    }

    @EventSourcingHandler
    public void on(ChatterLeaved event) {
        // TODO 会级联父级也被删除掉
        //markDeleted();
    }
}
