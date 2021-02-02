package com.feng.axon.api;

import com.feng.axon.model.ChatRoomId;
import com.feng.axon.query.AllRoomsQuery;
import com.feng.axon.query.RoomChatterQuery;
import com.feng.axon.query.RoomMessagesQuery;
import com.feng.axon.repository.ChatMessage;
import com.feng.axon.repository.Chatter;
import com.feng.axon.repository.Room;
import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.GenericQueryMessage;
import org.axonframework.queryhandling.QueryBus;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
public class ApiQueryController {

    private final QueryBus queryBus;
    private final QueryGateway queryGateway;

    @GetMapping("/rooms")
    public Mono<List<Room>> listRooms() {
//        signal
//        CompletableFuture<RoomSummary> query1 = queryGateway.query(new AllRoomsQuery(), ResponseTypes.instanceOf(RoomSummary.class));
        return Mono.fromFuture(queryGateway.query(new AllRoomsQuery(), ResponseTypes.multipleInstancesOf(Room.class)));
    }

    @GetMapping(value = "/rooms/{roomId}/chatters", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chatter> chatters(@PathVariable ChatRoomId roomId) {
        SubscriptionQueryResult<List<Chatter>, Chatter> result;

        result = queryGateway.subscriptionQuery(new RoomChatterQuery(roomId),
                ResponseTypes.multipleInstancesOf(Chatter.class), ResponseTypes.instanceOf(Chatter.class));

        Flux<Chatter> initData = result.initialResult().flatMapMany(Flux::fromIterable);

        return Flux.concat(initData, result.updates());
    }

    //归并查询
    @GetMapping("/rooms/{roomId}/chatters/combine")
    public Flux<Chatter> scatterGatherQueries(@PathVariable ChatRoomId roomId) {
        // create a query message
        GenericQueryMessage<RoomChatterQuery, List<Chatter>> query =
                new GenericQueryMessage<>(new RoomChatterQuery(roomId), "room", ResponseTypes.multipleInstancesOf(Chatter.class));
        query.withMetaData(ImmutableMap.of("userId", "axonUser"));

        return Flux.fromStream(queryBus.scatterGather(query, 10, TimeUnit.SECONDS)
                .map(Message::getPayload)
                .flatMap(Collection::stream).distinct());
    }

    @GetMapping(value = "/rooms/{roomId}/messages")
    public Mono<List<ChatMessage>> subscribeRoomMessages(@PathVariable ChatRoomId roomId) {
        return Mono.fromFuture(queryGateway.query(new RoomMessagesQuery(roomId), ResponseTypes.multipleInstancesOf(ChatMessage.class)));
    }

}
