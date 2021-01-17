package com.feng.axon.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyUpdated {
    private String companyId;
    private String companyName;
}
