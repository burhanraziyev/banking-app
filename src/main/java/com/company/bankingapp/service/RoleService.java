package com.company.bankingapp.service;

import com.company.bankingapp.model.dto.Role;
import com.company.bankingapp.model.entity.RoleEntity;
import com.company.bankingapp.model.enums.RoleType;
import com.company.bankingapp.model.request.CreateRoleRequest;

import java.util.List;

public interface RoleService {
    RoleEntity findRoleByRoleType(RoleType roleType);

    void createRole(CreateRoleRequest createRoleRequest);

    List<Role> getAllRoles();

}
