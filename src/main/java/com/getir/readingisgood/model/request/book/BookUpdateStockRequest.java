package com.getir.readingisgood.model.request.book;

import lombok.Data;

@Data
public class BookUpdateStockRequest {
    private Integer id;
    private int stock;
}
