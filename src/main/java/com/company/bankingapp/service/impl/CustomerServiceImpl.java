package com.company.bankingapp.service.impl;

import com.company.bankingapp.exception.ResourceExistsException;
import com.company.bankingapp.exception.ResourceNotFoundException;
import com.company.bankingapp.mapper.CustomerMapper;
import com.company.bankingapp.model.dto.Customer;
import com.company.bankingapp.model.entity.CustomerEntity;
import com.company.bankingapp.model.request.RegisterRequest;
import com.company.bankingapp.repository.CustomerRepository;
import com.company.bankingapp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Customer getCustomerByUsername(String username) {
        return customerMapper.toCustomer(findCustomerByUsername(username));
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerMapper.toCustomer(findCustomerById(id));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerMapper.toCustomerList(customerRepository.findAll());
    }

    @Override
    public Customer register(RegisterRequest request) {
        if (existsCustomerByUsername(request.getUsername()))
            throw new ResourceExistsException(
                    String.format("Customer exists with username: %s", request.getUsername()));

        CustomerEntity customerEntity = customerMapper.toCustomerEntity(request);
        return customerMapper.toCustomer(customerRepository.save(customerEntity));
    }

    @Override
    public CustomerEntity findCustomerByUsername(String username) {
        return customerRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Customer not found with username: %s", username)));
    }

    @Override
    public CustomerEntity findCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Customer not found with id: %s", id)));
    }

    private boolean existsCustomerByUsername(String username) {
        return customerRepository.existsByUsername(username);
    }
}
