package com.getir.readingisgood.model.request.order;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class CreateOrderRequest {
    @NotNull
    private Long customerId;
    @NotEmpty
    private List<CreateOrderRequestBooks> requestBooks;
}
