package com.feng.axon.config.command;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;

import java.util.List;
import java.util.function.BiFunction;

@Slf4j
public class MySimpleCommandDispatchInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {
            log.debug("send command :" + command.toString());
            return command;
        };
    }

}
