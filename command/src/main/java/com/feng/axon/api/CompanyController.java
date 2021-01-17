package com.feng.axon.api;

import com.feng.axon.command.CreateCompanyCommand;
import com.feng.axon.config.command.MyCommandGateway;
import com.feng.axon.model.CompanyId;
import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.MetaData;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final MyCommandGateway commandGateway;

    /**
     * 创建公司
     *
     * @param command
     * @return
     */
    @PostMapping("/company")
    @ResponseStatus(HttpStatus.CREATED)
    public Future<Map<String, String>> createChatRoom(@RequestBody @Valid CreateCompanyCommand command) {
        Assert.notNull(command.getCompanyName(), "participant is mandatory for a company");
        CompanyId companyId = CompanyId.newId();
        command.setCompanyId(companyId);
        Map<String, String> mapDate = new HashMap<>();
        mapDate.put("key", "val");
        commandGateway.send(command, MetaData.from(mapDate));
        return CompletableFuture.completedFuture(ImmutableMap.of("id", companyId.toString()));
    }

}
