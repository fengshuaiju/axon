package com.feng.axon.config.command;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.messaging.interceptors.JSR303ViolationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

//命令调度拦截器:在命令总线上发送命令时调用消息分派拦截器
@Slf4j
public class CommandValidationInterceptor extends BeanValidationInterceptor<CommandMessage<?>> {

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(List<? extends CommandMessage<?>> messages) {
        return (index, message) -> {
            //1:可以对消息进行拦截记录日志
            Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
            Set<ConstraintViolation<Object>> violations = validateMessage(message.getPayload(), validator);
            if (violations != null && !violations.isEmpty()) {
                //定义自己的消息返回体，JSR303校验不通过
                throw new JSR303ViolationException(violations);
            }
            return message;
        };
    }
}
