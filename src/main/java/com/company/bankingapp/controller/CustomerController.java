package com.company.bankingapp.controller;

import com.company.bankingapp.model.dto.Customer;
import com.company.bankingapp.model.response.ApiDataResponse;
import com.company.bankingapp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/{username}")
    public ResponseEntity<ApiDataResponse<Customer>> getCustomerByUsername(
            @PathVariable @NotNull String username) {
        Customer customer = customerService.getCustomerByUsername(username);
        return new ResponseEntity<>(new ApiDataResponse<>(customer), HttpStatus.OK);
    }

}
