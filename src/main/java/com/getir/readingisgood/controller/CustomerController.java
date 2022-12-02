package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.request.customer.CustomerLoginRequest;
import com.getir.readingisgood.model.request.customer.CustomerRegisterRequest;
import com.getir.readingisgood.model.response.BaseResponse;
import com.getir.readingisgood.service.customer.CustomerServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerServiceImp customerService;


    @PostMapping("/auth/register")
    public BaseResponse register(@Valid @RequestBody final CustomerRegisterRequest customerRegisterRequest){
        return customerService.register(customerRegisterRequest);
    }

    @GetMapping("/auth/login")
    public BaseResponse login(@Valid @RequestBody final CustomerLoginRequest customerLoginRequest){
        return customerService.login(customerLoginRequest);
    }

}
