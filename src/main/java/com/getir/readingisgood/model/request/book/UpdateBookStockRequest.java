package com.getir.readingisgood.model.request.book;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateBookStockRequest {
    private Integer bookId;
    private int stock;
}
