package com.feng.axon.config.event;

import org.axonframework.config.Configurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventProcessorConfiguration {
    public void configureEventProcessing(Configurer configurer) {
        configurer.eventProcessing()
                .registerTrackingEventProcessor("my-tracking-processor")
                .registerHandlerInterceptor("my-tracking-processor", configuration -> new MyEventHandlerInterceptor());
    }
}
