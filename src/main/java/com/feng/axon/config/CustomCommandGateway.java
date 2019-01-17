package com.feng.axon.config;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGatewayFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CustomCommandGateway {

    private final CommandBus commandBus;

    @Autowired
    public CustomCommandGateway(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @Bean
    public MyCommandGateway commandGatewayFactory(){
        CommandGatewayFactory factory = CommandGatewayFactory.builder().commandBus(commandBus).build();
        return factory.createGateway(MyCommandGateway.class);
    }
}
