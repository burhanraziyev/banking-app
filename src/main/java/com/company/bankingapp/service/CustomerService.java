package com.company.bankingapp.service;

import com.company.bankingapp.model.dto.Customer;
import com.company.bankingapp.model.entity.CustomerEntity;
import com.company.bankingapp.model.request.RegisterRequest;

import java.util.List;

public interface CustomerService {
    Customer getCustomerByUsername(String username);
    Customer getCustomerById(Long id);

    List<Customer> getAllCustomers();

    Customer register(RegisterRequest request);

    CustomerEntity findCustomerByUsername(String username);
    CustomerEntity findCustomerById(Long id);

}
