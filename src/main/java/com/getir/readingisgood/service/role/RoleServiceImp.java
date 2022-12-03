package com.getir.readingisgood.service.role;

import com.getir.readingisgood.entity.Role;
import com.getir.readingisgood.enums.ERole;
import com.getir.readingisgood.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImp implements RoleService{
    private final RoleRepository roleRepository;

    //inserts missing roles in db when app start
    @PostConstruct
    public void saveRolesToDBWhenAppStart(){ //TODO check later for better logic
        List<Role> roles = roleRepository.findAll();

        boolean isAdminRoleExists = false;
        boolean isUserRoleExists = false;

        for(Role role : roles){
            if(role.getName().equals(ERole.ADMIN)){
                isAdminRoleExists = true;
            } else if(role.getName().equals(ERole.USER)){
                isUserRoleExists = true;
            }
        }

        if(!isAdminRoleExists){
            Role adminRole = Role.builder().name(ERole.ADMIN).build();
            roleRepository.save(adminRole);
        }
        if(!isUserRoleExists){
            Role userRole = Role.builder().name(ERole.USER).build();
            roleRepository.save(userRole);
        }


    }

    public Role findByName(final ERole name){
        return roleRepository.findByName(name);
    }
}
