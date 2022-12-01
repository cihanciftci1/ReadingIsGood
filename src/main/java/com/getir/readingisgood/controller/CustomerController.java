package com.getir.readingisgood.controller;

import com.getir.readingisgood.enums.IConstants;
import com.getir.readingisgood.manager.customer.CustomerManager;
import com.getir.readingisgood.model.request.customer.CustomerLoginRequest;
import com.getir.readingisgood.model.request.customer.CustomerRegisterRequest;
import com.getir.readingisgood.model.response.customer.CustomerLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerManager customerManager;


    @PostMapping("/auth/register")
    public ResponseEntity<String> register(@Valid @RequestBody final CustomerRegisterRequest customerRegisterRequest){
        String result = customerManager.register(customerRegisterRequest);

        if(result.equals(IConstants.USERNAME_ALREADY_TAKEN) || result.equals(IConstants.EMAIL_ALREADY_TAKEN)){
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/auth/login")
    public ResponseEntity<CustomerLoginResponse> login(@Valid @RequestBody final CustomerLoginRequest customerLoginRequest){
        return new ResponseEntity<>(customerManager.login(customerLoginRequest), HttpStatus.OK);
    }

}
