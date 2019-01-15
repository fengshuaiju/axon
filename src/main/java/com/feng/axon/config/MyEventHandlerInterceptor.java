package com.feng.axon.config;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.GenericDomainEventMessage;
import org.axonframework.messaging.InterceptorChain;
import org.axonframework.messaging.MessageHandlerInterceptor;
import org.axonframework.messaging.unitofwork.UnitOfWork;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyEventHandlerInterceptor implements MessageHandlerInterceptor<EventMessage<?>> {

    @Override
    public Object handle(UnitOfWork<? extends EventMessage<?>> unitOfWork, InterceptorChain interceptorChain) throws Exception {
        EventMessage<?> message = unitOfWork.getMessage();

        if (message instanceof GenericDomainEventMessage) {
            log.info("Projecting event {} from aggregate {} with id {}",
                    message.getPayloadType(), ((GenericDomainEventMessage<?>) message).getType(),
                    ((GenericDomainEventMessage)message).getAggregateIdentifier());
        } else {
            log.info("Projecting event {}", message.getPayloadType());
        }

        return interceptorChain.proceed();
    }
}
