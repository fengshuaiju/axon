package com.feng.axon.config.command;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.InterceptorChain;
import org.axonframework.messaging.MessageHandlerInterceptor;
import org.axonframework.messaging.unitofwork.UnitOfWork;

import java.util.Optional;

//处理程序拦截器是在命令处理程序的上下文中调用的。这意味着它们可以基于所处理的消息将相关数据附加到工作单元
@Slf4j
public class MyCommandHandlerInterceptor implements MessageHandlerInterceptor<CommandMessage<?>> {

    @Override
    public Object handle(UnitOfWork<? extends CommandMessage<?>> unitOfWork, InterceptorChain interceptorChain) throws Exception {
        CommandMessage<?> command = unitOfWork.getMessage();
        //该拦截器只允许处理包含 axonUser 作为元数据中 userId 字段值的命令 如果 userId 的值不匹配 axonUser，不会继续向下调用。
        String userId = Optional.ofNullable(command.getMetaData().get("userId"))
                .map(uId -> (String) uId)
                //IllegalCommandException
                .orElseThrow(IllegalArgumentException::new);
        if ("axonUser".equals(userId)) {
            return interceptorChain.proceed();
        }
        return null;
    }
}
