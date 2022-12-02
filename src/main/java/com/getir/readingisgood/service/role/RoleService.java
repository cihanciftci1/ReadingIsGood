package com.getir.readingisgood.service.role;

import com.getir.readingisgood.entity.Role;
import com.getir.readingisgood.enums.ERole;

import java.util.Optional;

public interface RoleService {
    void saveRolesToDBWhenAppStart();
    Optional<Role> findByName(final ERole name);
}
