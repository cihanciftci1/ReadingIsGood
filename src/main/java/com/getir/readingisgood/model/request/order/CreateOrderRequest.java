package com.getir.readingisgood.model.request.order;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateOrderRequest {
    @NotNull
    private Long customerId;
    @NotEmpty
    private List<CreateOrderRequestBooks> orderBooks;
}
