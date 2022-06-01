package com.company.bankingapp.repository;

import com.company.bankingapp.model.dto.Customer;
import com.company.bankingapp.model.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByUsername(String username);
    Boolean existsByUsername(String username);
}