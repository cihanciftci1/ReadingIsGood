package com.getir.readingisgood.service.role;

import com.getir.readingisgood.entity.Role;
import com.getir.readingisgood.enums.ERole;
import com.getir.readingisgood.repository.role.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final IRoleRepository roleRepository;

    @PostConstruct
    public void saveRolesToDBWhenAppStart(){ //TODO check later
        List<Role> roles = roleRepository.findAll();
        if(roles.isEmpty()){
            Role adminRole = Role.builder().name(ERole.ADMIN).build();
            roleRepository.save(adminRole);

            Role userRole = Role.builder().name(ERole.USER).build();
            roleRepository.save(userRole);
        }
    }

    public Optional<Role> findByName(final ERole name){
        return roleRepository.findByName(name);
    }
}
