package com.feng.axon.config.query;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.InterceptorChain;
import org.axonframework.messaging.MessageHandlerInterceptor;
import org.axonframework.messaging.unitofwork.UnitOfWork;
import org.axonframework.queryhandling.QueryMessage;

import java.util.Optional;

//消息处理程序拦截器可以在查询处理之前和之后执行操作。拦截器甚至可以完全阻止查询处理，例如出于安全原因。
@Slf4j
public class MyQueryHandlerInterceptor implements MessageHandlerInterceptor<QueryMessage<?, ?>> {

    @Override
    public Object handle(UnitOfWork<? extends QueryMessage<?, ?>> unitOfWork, InterceptorChain interceptorChain) throws Exception {

        log.info("====>>>>MyQueryHandlerInterceptor");

//        QueryMessage<?, ?> queryMessage = unitOfWork.getMessage();
//
//        //该拦截器只允许处理包含 axonUser 作为元数据中 userId 字段值的命令 如果 userId 的值不匹配 axonUser，不会继续向下调用。
//        String userId = Optional.ofNullable(queryMessage.getMetaData().get("userId"))
//                .map(uId -> (String) uId)
//                //IllegalCommandException
//                .orElseThrow(IllegalArgumentException::new);
//        if ("axonUser".equals(userId)) {
//            return interceptorChain.proceed();
//        }
//        return null;

        return interceptorChain.proceed();
    }

}
