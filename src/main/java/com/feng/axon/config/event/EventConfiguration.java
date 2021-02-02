package com.feng.axon.config.event;

import org.axonframework.axonserver.connector.event.axon.AxonServerEventStore;
import org.axonframework.config.Configurer;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.eventstore.AbstractEventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.SequenceEventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfiguration {

    public void configureEventProcessing(Configurer configurer) {
        //注册事件处理程序拦截器
        configurer.eventProcessing()
                .registerTrackingEventProcessor("my-tracking-processor")
                .registerHandlerInterceptor("my-tracking-processor",
                        configuration -> new MyEventHandlerInterceptor());
    }

//    @Bean
//    public EventStorageEngine eventStorageEngine(){
//        eventStorageEngine = new InMemoryEventStorageEngine();
//        return eventStorageEngine;
//    }
//
//    @Bean
//    public EventBus configureEventBus() {
//        // note that an EventStore is a more specific implementation of an EventBus
//        EventBus eventBus = EmbeddedEventStore.builder().storageEngine(eventStorageEngine).build();
//        //注册事件分派拦截器
//        eventBus.registerDispatchInterceptor(new EventLoggingDispatchInterceptor());
//        return eventBus;
//    }

}
