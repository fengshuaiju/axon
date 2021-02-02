package com.feng.axon.config.query;

import org.axonframework.queryhandling.SimpleQueryBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryConfiguration {
    @Bean
    public SimpleQueryBus simpleQueryBus(){
        SimpleQueryBus simpleQueryBus = SimpleQueryBus.builder().build();
        simpleQueryBus.registerHandlerInterceptor(new MyQueryHandlerInterceptor());
        simpleQueryBus.registerDispatchInterceptor(new QueryValidationInterceptor());
        return simpleQueryBus;
    }
}
