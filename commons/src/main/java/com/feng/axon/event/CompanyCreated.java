package com.feng.axon.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyCreated {
    private String companyId;
    private String companyName;
}
