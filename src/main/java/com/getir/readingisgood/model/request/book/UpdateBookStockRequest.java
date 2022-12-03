package com.getir.readingisgood.model.request.book;

import lombok.Data;

@Data
public class UpdateBookStockRequest {
    private Integer id;
    private int stock;
}
