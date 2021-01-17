package com.feng.axon.command;

import com.feng.axon.model.CompanyId;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class CompanyCommand {
    @TargetAggregateIdentifier
    private CompanyId companyId;
}
