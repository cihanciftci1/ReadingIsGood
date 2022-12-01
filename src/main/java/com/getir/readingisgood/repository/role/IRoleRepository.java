package com.getir.readingisgood.repository.role;

import com.getir.readingisgood.entity.Role;
import com.getir.readingisgood.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(final ERole name);
}
