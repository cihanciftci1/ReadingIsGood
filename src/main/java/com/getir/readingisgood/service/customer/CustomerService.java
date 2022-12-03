package com.getir.readingisgood.service.customer;

import com.getir.readingisgood.model.request.customer.GetCustomerOrdersRequest;
import com.getir.readingisgood.model.request.customer.LoginCustomerRequest;
import com.getir.readingisgood.model.request.customer.RegisterCustomerRequest;
import com.getir.readingisgood.model.response.BaseResponse;

public interface CustomerService {
    BaseResponse register(final RegisterCustomerRequest registerCustomerRequest);
    BaseResponse login(final LoginCustomerRequest loginCustomerRequest);
    BaseResponse getCustomerOrders(final GetCustomerOrdersRequest getCustomerOrdersRequest);
}
