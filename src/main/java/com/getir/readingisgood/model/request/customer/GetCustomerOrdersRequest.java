package com.getir.readingisgood.model.request.customer;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GetCustomerOrdersRequest {
    @NotNull
    private Long customerId;

    private Integer pageIndex;

    private Integer pageSize;
}
