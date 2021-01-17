package com.feng.axon.repository;

import com.feng.axon.entity.Company;
import com.feng.axon.event.CompanyCreated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompanyProjection {

    private final MyCompanyRepository myCompanyRepository;

    @EventHandler
    public void on(CompanyCreated event, @Timestamp Instant createdAt) {
        myCompanyRepository.save(new Company(event.getCompanyId(), event.getCompanyName(), createdAt));
    }

    public List<Company> on() {
        return myCompanyRepository.findAll();
    }

}
