package com.getir.readingisgood.model.request.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CreateOrderRequestBooks {
    private Integer bookId;
    private int quantity;
}
