package com.feng.axon.config.command;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGatewayFactory;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.messaging.interceptors.CorrelationDataInterceptor;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandBusConfiguration {

    @Bean
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public SimpleCommandBus commandBus(TransactionManager txManager, AxonConfiguration axonConfiguration) {
        SimpleCommandBus commandBus =
                SimpleCommandBus.builder()
                        .transactionManager(txManager)
                        .messageMonitor(axonConfiguration.messageMonitor(CommandBus.class, "commandBus"))
                        .build();
        commandBus.registerDispatchInterceptor(new MySimpleCommandDispatchInterceptor());
        commandBus.registerDispatchInterceptor(new MyValidationCommandDispatchInterceptor());
        commandBus.registerHandlerInterceptor(new CorrelationDataInterceptor<>(axonConfiguration.correlationDataProviders()));

        return commandBus;
    }

}
