package com.getir.readingisgood.repository.role;

import com.getir.readingisgood.entity.Role;
import com.getir.readingisgood.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(final ERole name);
}
