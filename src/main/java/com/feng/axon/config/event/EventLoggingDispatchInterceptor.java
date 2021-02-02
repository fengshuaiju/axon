package com.feng.axon.config.event;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;

import java.util.List;
import java.util.function.BiFunction;

//事件分派拦截器
//事件消息分派拦截器
//在事件总线上注册的任何消息分派拦截器都将在事件发布时被调用。它们可以通过添加元数据来更改事件消息。它们还可以为事件发布时提供全面的日志记录功能。这些拦截器总是在发布事件的线程上调用。
@Slf4j
public class EventLoggingDispatchInterceptor implements MessageDispatchInterceptor<EventMessage<?>> {

    @Override
    public BiFunction<Integer, EventMessage<?>, EventMessage<?>> handle(List<? extends EventMessage<?>> messages) {
        return (index, event) -> {
            //记录事件日志
            //变更事件元数据
            event.withMetaData(ImmutableMap.of("dispatch", "dispatchUpdated"));
            return event;
        };
    }
}