package com.feng.axon.api;

import com.feng.axon.query.AllRoomsQuery;
import com.feng.axon.query.RoomMessagesQuery;
import com.feng.axon.repository.ChatMessage;
import com.feng.axon.repository.RoomSummary;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.MultipleInstancesResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf;
import static org.axonframework.messaging.responsetypes.ResponseTypes.multipleInstancesOf;

@RestController
@RequiredArgsConstructor
public class ApiQueryController {

    private final QueryGateway queryGateway;

    @GetMapping("rooms")
    public Future<List<RoomSummary>> listRooms() {
//        CompletableFuture<List<RoomSummary>> query2 = queryGateway.query(new AllRoomsQuery(), new MultipleInstancesResponseType<>(RoomSummary.class));
        CompletableFuture<List<RoomSummary>> query = queryGateway.query(new AllRoomsQuery(), ResponseTypes.multipleInstancesOf(RoomSummary.class));
//        CompletableFuture<RoomSummary> query1 = queryGateway.query(new AllRoomsQuery(), ResponseTypes.instanceOf(RoomSummary.class));
        return query;
    }


    @GetMapping(value = "/rooms/{roomId}/messages/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatMessage> subscribeRoomMessages(@PathVariable String roomId) {
        RoomMessagesQuery query = new RoomMessagesQuery(roomId);
        SubscriptionQueryResult<List<ChatMessage>, ChatMessage> result;
        result = queryGateway.subscriptionQuery(query, multipleInstancesOf(ChatMessage.class), instanceOf(ChatMessage.class));
        Flux<ChatMessage> initialResult = result.initialResult().flatMapMany(Flux::fromIterable);
        return Flux.concat(initialResult, result.updates());
    }

}
