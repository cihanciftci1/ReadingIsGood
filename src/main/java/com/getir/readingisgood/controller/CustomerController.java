package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.request.customer.GetCustomerOrdersRequest;
import com.getir.readingisgood.model.request.customer.LoginCustomerRequest;
import com.getir.readingisgood.model.request.customer.RegisterCustomerRequest;
import com.getir.readingisgood.model.response.BaseResponse;
import com.getir.readingisgood.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;


    @PostMapping("/auth/register")
    public BaseResponse customerRegister(@Valid @RequestBody final RegisterCustomerRequest registerCustomerRequest){
        return customerService.register(registerCustomerRequest);
    }

    @GetMapping("/auth/login")
    public BaseResponse customerLogin(@Valid @RequestBody final LoginCustomerRequest loginCustomerRequest){
        return customerService.login(loginCustomerRequest);
    }

    @GetMapping("/orders/get")
    public BaseResponse getCustomerOrders(@Valid @RequestBody final GetCustomerOrdersRequest getCustomerOrdersRequest){
        return customerService.getCustomerOrders(getCustomerOrdersRequest);
    }


}
