package com.company.bankingapp.model.request;

import com.company.bankingapp.model.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoleRequest {
    private RoleType roleType;
}
