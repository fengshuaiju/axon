package com.feng.axon.config.command;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.messaging.interceptors.JSR303ViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.function.BiFunction;

public class MyValidationCommandDispatchInterceptor extends BeanValidationInterceptor<CommandMessage<?>> {

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(List<? extends CommandMessage<?>> messages) {
        try {
            return super.handle(messages);
        } catch (JSR303ViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
