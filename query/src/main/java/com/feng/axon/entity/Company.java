package com.feng.axon.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
public class Company {
    @Id
    private String companyId;
    private String name;
    private Instant createdAt;

    public Company(String companyId, String name, Instant createdAt) {
        this.companyId = companyId;
        this.name = name;
        this.createdAt = createdAt;
    }

}
