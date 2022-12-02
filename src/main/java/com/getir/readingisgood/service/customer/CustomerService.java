package com.getir.readingisgood.service.customer;

import com.getir.readingisgood.model.request.customer.CustomerLoginRequest;
import com.getir.readingisgood.model.request.customer.CustomerRegisterRequest;
import com.getir.readingisgood.model.response.BaseResponse;

public interface CustomerService {
    BaseResponse register(final CustomerRegisterRequest customerRegisterRequest);
    BaseResponse login(final CustomerLoginRequest customerLoginRequest);
}
