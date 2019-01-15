package com.feng.axon.config;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.Configurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class EventProcessorConfiguration {

    @Autowired
    private MyEventHandlerInterceptor myEventHandlerInterceptor;

    @Autowired
    public void configureEventProcessing(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") Configurer configurer) {
        configurer.eventProcessing().registerDefaultHandlerInterceptor((config, name) -> this.myEventHandlerInterceptor);
    }

}
