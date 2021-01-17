package com.feng.axon.aggregate;

import com.feng.axon.command.CompanyCommand;
import com.feng.axon.command.CreateCompanyCommand;
import com.feng.axon.event.CompanyCreated;
import com.feng.axon.model.CompanyId;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.InterceptorChain;
import org.axonframework.messaging.MetaData;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateVersion;
import org.axonframework.modelling.command.CommandHandlerInterceptor;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Slf4j
@Aggregate
@NoArgsConstructor
public class Company {

    @AggregateIdentifier
    private CompanyId companyId;

    @AggregateVersion
    private long version;

    private String companyName;

    @SneakyThrows
    @CommandHandlerInterceptor
    public void intercept(CompanyCommand command, InterceptorChain interceptorChain) {
        if (command.getClass() != CreateCompanyCommand.class && this.companyId.equals(command.getCompanyId())) {
            interceptorChain.proceed();
        }
    }

    @CommandHandler
    public Company(CreateCompanyCommand command, MetaData metaData) {
        log.info(metaData.toString() + "@ " + metaData.get("key").toString());
        apply(new CompanyCreated(command.getCompanyId().toString(), command.getCompanyName()), metaData);
    }

    @EventSourcingHandler
    protected void on(CompanyCreated event) {
        this.companyId = new CompanyId(event.getCompanyId());
        this.companyName = event.getCompanyName();
    }

}
