package com.getir.readingisgood.manager.customer;

import com.getir.readingisgood.entity.Customer;
import com.getir.readingisgood.entity.Role;
import com.getir.readingisgood.enums.ERole;
import com.getir.readingisgood.enums.IConstants;
import com.getir.readingisgood.model.request.customer.CustomerLoginRequest;
import com.getir.readingisgood.model.request.customer.CustomerRegisterRequest;
import com.getir.readingisgood.model.response.customer.CustomerLoginResponse;
import com.getir.readingisgood.repository.role.IRoleRepository;
import com.getir.readingisgood.service.customer.CustomerService;
import com.getir.readingisgood.service.security.JWTGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CustomerManager {
    private final AuthenticationManager authenticationManager;
    private final IRoleRepository roleService;
    private final JWTGenerator jwtGenerator;
    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;


    public Optional<Customer> findByUsername(final String username){
        return customerService.findByUsername(username);
    }

    public boolean existsByUsername(final String username){
        return customerService.existsByUsername(username);
    }

    public boolean existsByEmail(final String email){
        return customerService.existsByEmail(email);
    }

    public void save(final Customer customer){
        customerService.save(customer);
    }

    public String register(final CustomerRegisterRequest customerRegisterRequest){
        if(customerService.existsByUsername(customerRegisterRequest.getUsername())){
            return IConstants.USERNAME_ALREADY_TAKEN;
        }else if (customerService.existsByEmail(customerRegisterRequest.getEmail())){
            return IConstants.EMAIL_ALREADY_TAKEN;
        }

        Role role = roleService.findByName(ERole.USER).get();

        Customer customer = Customer.builder()
                .username(customerRegisterRequest.getUsername())
                .email(customerRegisterRequest.getEmail())
                .password(passwordEncoder.encode(customerRegisterRequest.getPassword()))
                .roles(Set.of(role))
                .build();

        save(customer);

        return IConstants.REGISTER_SUCCESSFUL;
    }

    public CustomerLoginResponse login(final CustomerLoginRequest customerLoginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                customerLoginRequest.getUsername(), customerLoginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);

        return CustomerLoginResponse.builder().token(token).build();
    }
}
