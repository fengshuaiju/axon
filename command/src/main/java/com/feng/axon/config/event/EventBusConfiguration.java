package com.feng.axon.config.event;

import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventBusConfiguration {
    public EventBus configureEventBus(EventStorageEngine eventStorageEngine) {
        // note that an EventStore is a more specific implementation of an EventBus
        EventBus eventBus = EmbeddedEventStore.builder()
                .storageEngine(eventStorageEngine)
                .build();
        eventBus.registerDispatchInterceptor(new EventLoggingDispatchInterceptor());
        return eventBus;
    }
}
