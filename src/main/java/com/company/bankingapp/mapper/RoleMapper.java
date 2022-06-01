package com.company.bankingapp.mapper;

import com.company.bankingapp.model.dto.Role;
import com.company.bankingapp.model.entity.RoleEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleMapper {

    public Role toRole(RoleEntity from){
        return new Role(
                from.getId(),
                from.getRoleType(),
                from.getCreatedBy(),
                from.getCreatedDate(),
                from.getLastModifiedBy(),
                from.getLastModifiedDate()
        );
    }

    public Set<Role> toRoleSet(Set<RoleEntity>roleEntities){
        return roleEntities.stream().map(this::toRole).collect(Collectors.toSet());
    }

    public List<Role> toRoleList(List<RoleEntity>roleEntities){
        return roleEntities.stream().map(this::toRole).collect(Collectors.toList());
    }
}
