package com.getir.readingisgood.service.role;

import com.getir.readingisgood.entity.Role;
import com.getir.readingisgood.enums.ERole;

public interface RoleService {
    void saveRolesToDBWhenAppStart();
    Role findByName(final ERole name);
}
