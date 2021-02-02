package com.feng.axon.config.event;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.messaging.InterceptorChain;
import org.axonframework.messaging.MessageHandlerInterceptor;
import org.axonframework.messaging.unitofwork.UnitOfWork;

import java.util.Optional;

//MyEventHandlerInterceptor
//处理程序拦截器是在事件处理程序的上下文中调用的，它们可以基于所处理的消息将相关数据附加到工作单元
//下面示例只允许处理包含 axonUser 作为元数据中 userId 字段值的事件。如果元数据中没有 userId，则将引发一个异常
@Slf4j
public class MyEventHandlerInterceptor implements MessageHandlerInterceptor<EventMessage<?>> {

    @Override
    public Object handle(UnitOfWork<? extends EventMessage<?>> unitOfWork,
                         InterceptorChain interceptorChain) throws Exception {
        EventMessage<?> event = unitOfWork.getMessage();
        String userId = Optional.ofNullable(event.getMetaData().get("userId"))
                .map(uId -> (String) uId)
                //IllegalEventException
                .orElseThrow(IllegalArgumentException::new);
        if ("axonUser".equals(userId)) {
            return interceptorChain.proceed();
        }
        return null;
    }
}
