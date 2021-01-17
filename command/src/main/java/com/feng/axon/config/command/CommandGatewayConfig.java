package com.feng.axon.config.command;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGatewayFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandGatewayConfig {
    private final CommandBus commandBus;

    public CommandGatewayConfig(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @Bean
    public MyCommandGateway myCommandGateway() {
        CommandGatewayFactory factory = CommandGatewayFactory.builder().commandBus(commandBus).build();
        return factory.createGateway(MyCommandGateway.class);
    }
}
