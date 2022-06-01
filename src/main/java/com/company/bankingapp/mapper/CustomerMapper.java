package com.company.bankingapp.mapper;

import com.company.bankingapp.model.dto.Customer;
import com.company.bankingapp.model.entity.CustomerEntity;
import com.company.bankingapp.model.enums.RoleType;
import com.company.bankingapp.model.request.RegisterRequest;
import com.company.bankingapp.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.company.bankingapp.model.enums.RoleType.ROLE_USER;

@Component
@RequiredArgsConstructor
public class CustomerMapper {

    private final RoleMapper roleMapper;
    private final RoleService roleService;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;

    public List<Customer> toCustomerList(List<CustomerEntity> customerEntities) {
        return customerEntities.stream().map(this::toCustomer).collect(Collectors.toList());
    }

    public Customer toCustomer(CustomerEntity from) {
        return new Customer(
                from.getId(),
                from.getUsername(),
                from.getPassword(),
                from.getFirstName(),
                from.getLastName(),
                accountMapper.toAccountSet(from.getAccountEntities()),
                roleMapper.toRoleSet(from.getRoleEntities()),
                from.getCreatedBy(),
                from.getCreatedDate(),
                from.getLastModifiedBy(),
                from.getLastModifiedDate()
        );
    }


    public CustomerEntity toCustomerEntity(RegisterRequest from) {
        return CustomerEntity.builder()
                .firstName(from.getFirstName())
                .lastName(from.getLastName())
                .password(passwordEncoder.encode(from.getPassword()))
                .roleEntities(Set.of(roleService.findRoleByRoleType(ROLE_USER)))
                .username(from.getUsername())
                .build();
    }
}
