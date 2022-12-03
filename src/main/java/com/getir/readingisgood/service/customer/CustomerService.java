package com.getir.readingisgood.service.customer;

import com.getir.readingisgood.entity.Customer;
import com.getir.readingisgood.model.request.customer.GetCustomerOrdersRequest;
import com.getir.readingisgood.model.request.customer.LoginCustomerRequest;
import com.getir.readingisgood.model.request.customer.RegisterCustomerRequest;
import com.getir.readingisgood.model.response.BaseResponse;

import java.util.Optional;

public interface CustomerService {
    BaseResponse register(final RegisterCustomerRequest registerCustomerRequest);
    BaseResponse login(final LoginCustomerRequest loginCustomerRequest);
    Optional<Customer> findById(final Long id);
    BaseResponse getCustomerOrders(final GetCustomerOrdersRequest getCustomerOrdersRequest);
}
