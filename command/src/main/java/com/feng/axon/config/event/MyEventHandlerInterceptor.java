package com.feng.axon.config.event;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.messaging.InterceptorChain;
import org.axonframework.messaging.MessageHandlerInterceptor;
import org.axonframework.messaging.unitofwork.UnitOfWork;

@Slf4j
public class MyEventHandlerInterceptor implements MessageHandlerInterceptor<EventMessage<?>> {

    @Override
    public Object handle(UnitOfWork<? extends EventMessage<?>> unitOfWork,
                         InterceptorChain interceptorChain) throws Exception {
        EventMessage<?> event = unitOfWork.getMessage();
//        String userId = Optional.ofNullable(event.getMetaData().get("userId"))
//                .map(uId -> (String) uId)
//                .orElseThrow(RuntimeException::new);

        log.debug("=================" + event.getIdentifier());

        return interceptorChain.proceed();
    }
}
