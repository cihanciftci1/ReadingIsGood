package com.getir.readingisgood.model.request.customer;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class GetCustomerOrdersRequest {
    @NotNull
    private Long customerId;

    private Integer pageIndex;

    private Integer pageSize;
}
