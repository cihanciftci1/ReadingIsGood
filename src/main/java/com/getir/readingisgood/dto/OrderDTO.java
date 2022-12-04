package com.getir.readingisgood.dto;

import com.getir.readingisgood.entity.BookOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class OrderDTO {
    private String customerEmail;

    private List<BookOrder> bookOrders;

    private int bookCount;

    private double totalAmount;

}
