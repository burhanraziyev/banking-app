package com.company.bankingapp.repository;

import com.company.bankingapp.model.entity.RoleEntity;
import com.company.bankingapp.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRoleType(RoleType roleType);
}