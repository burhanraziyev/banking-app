package com.company.bankingapp.service.impl;

import com.company.bankingapp.exception.ResourceNotFoundException;
import com.company.bankingapp.mapper.RoleMapper;
import com.company.bankingapp.model.dto.Role;
import com.company.bankingapp.model.entity.RoleEntity;
import com.company.bankingapp.model.enums.RoleType;
import com.company.bankingapp.model.request.CreateRoleRequest;
import com.company.bankingapp.repository.RoleRepository;
import com.company.bankingapp.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    @Override
    public RoleEntity findRoleByRoleType(RoleType roleType) {
        return roleRepository.findByRoleType(roleType)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Role not found with roleType: %s", roleType)));
    }

    @Override
    public void createRole(CreateRoleRequest createRoleRequest) {
        RoleEntity roleEntity = RoleEntity.builder().roleType(createRoleRequest.getRoleType()).build();
        roleRepository.save(roleEntity);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleMapper.toRoleList(roleRepository.findAll());
    }

}
