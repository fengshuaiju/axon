package com.feng.axon.api;

import com.feng.axon.query.AllRoomsQuery;
import com.feng.axon.query.RoomMessagesQuery;
import com.feng.axon.repository.ChatMessage;
import com.feng.axon.repository.RoomSummary;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequiredArgsConstructor
public class ApiQueryController {

    private final QueryGateway queryGateway;

    @GetMapping("rooms")
    public Future<List<RoomSummary>> listRooms() {
//        signal
//        CompletableFuture<RoomSummary> query1 = queryGateway.query(new AllRoomsQuery(), ResponseTypes.instanceOf(RoomSummary.class));
        CompletableFuture<List<RoomSummary>> query = queryGateway.query(new AllRoomsQuery(), ResponseTypes.multipleInstancesOf(RoomSummary.class));
        return query;
    }


    @GetMapping(value = "/rooms/{roomId}/messages/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatMessage> subscribeRoomMessages(@PathVariable String roomId) {
        SubscriptionQueryResult<List<ChatMessage>, ChatMessage> result;

        result = queryGateway.subscriptionQuery(new RoomMessagesQuery(roomId),
                ResponseTypes.multipleInstancesOf(ChatMessage.class), ResponseTypes.instanceOf(ChatMessage.class));

        Flux<ChatMessage> initialResult = result.initialResult().flatMapMany(Flux::fromIterable);

        return Flux.concat(initialResult, result.updates());
    }

}
