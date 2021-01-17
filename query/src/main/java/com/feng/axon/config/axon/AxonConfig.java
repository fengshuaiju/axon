package com.feng.axon.config.axon;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.extensions.kafka.eventhandling.KafkaMessageConverter;
import org.axonframework.messaging.interceptors.LoggingInterceptor;
import org.axonframework.serialization.Serializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    @Bean
    public KafkaMessageConverter<String, byte[]> kafkaMessageConverter(
            @Qualifier("eventSerializer") Serializer eventSerializer, ObjectMapper objectMapper) {
        return new MyKafkaMessageConverter(eventSerializer, objectMapper);
    }

    @Autowired
    public void registerLoggingInterceptor(EventProcessingConfigurer configurer) {
        configurer.registerDefaultHandlerInterceptor((conf, name) -> new LoggingInterceptor<>());
    }

}
