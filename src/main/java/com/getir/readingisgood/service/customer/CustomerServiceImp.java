package com.getir.readingisgood.service.customer;

import com.getir.readingisgood.entity.Customer;
import com.getir.readingisgood.entity.Role;
import com.getir.readingisgood.enums.Constants;
import com.getir.readingisgood.enums.ERole;
import com.getir.readingisgood.model.request.customer.CustomerLoginRequest;
import com.getir.readingisgood.model.request.customer.CustomerRegisterRequest;
import com.getir.readingisgood.model.response.BaseResponse;
import com.getir.readingisgood.model.response.customer.CustomerLoginResponse;
import com.getir.readingisgood.model.response.customer.CustomerRegisterResponse;
import com.getir.readingisgood.model.response.errors.AuthenticationErrorResponse;
import com.getir.readingisgood.model.response.errors.BadRequestErrorResponse;
import com.getir.readingisgood.repository.customer.CustomerRepository;
import com.getir.readingisgood.service.role.RoleServiceImp;
import com.getir.readingisgood.service.security.JWTGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomerServiceImp implements CustomerService{
    private final CustomerRepository customerRepository;
    private final AuthenticationManager authenticationManager;
    private final RoleServiceImp roleService;
    private final JWTGenerator jwtGenerator;
    private final PasswordEncoder passwordEncoder;

    public BaseResponse register(final CustomerRegisterRequest customerRegisterRequest){
        if(customerRepository.existsByUsername(customerRegisterRequest.getUsername())){
            return new BadRequestErrorResponse(Constants.USERNAME_ALREADY_TAKEN);
        }else if (customerRepository.existsByEmail(customerRegisterRequest.getEmail())){
            return new BadRequestErrorResponse(Constants.EMAIL_ALREADY_TAKEN);
        }

        Role role = roleService.findByName(ERole.USER).get();

        Customer customer = Customer.builder()
                .username(customerRegisterRequest.getUsername())
                .email(customerRegisterRequest.getEmail())
                .password(passwordEncoder.encode(customerRegisterRequest.getPassword()))
                .roles(Set.of(role))
                .build();

        customerRepository.save(customer);

        return new CustomerRegisterResponse(Constants.REGISTER_SUCCESSFUL);
    }

    public BaseResponse login(final CustomerLoginRequest customerLoginRequest){
        String token;
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    customerLoginRequest.getUsername(), customerLoginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtGenerator.generateToken(authentication);
        }catch (AuthenticationException e){
            return new AuthenticationErrorResponse(Constants.LOGIN_FAILED);
        }
        return new CustomerLoginResponse(Constants.LOGIN_SUCCESFULL, token);
    }

}
