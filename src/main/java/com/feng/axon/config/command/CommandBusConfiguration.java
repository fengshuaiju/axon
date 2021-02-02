package com.feng.axon.config.command;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandBusConfiguration {

    @Bean
    public CommandBus commandBus() {
        CommandBus commandBus = SimpleCommandBus.builder().build();
        commandBus.registerDispatchInterceptor(new CommandValidationInterceptor());
        commandBus.registerHandlerInterceptor(new MyCommandHandlerInterceptor());
        return commandBus;
    }

}
