package com.feng.axon.repository;

import com.feng.axon.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyCompanyRepository extends JpaRepository<Company, String> {
}
