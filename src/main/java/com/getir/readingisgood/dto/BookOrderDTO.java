package com.getir.readingisgood.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BookOrderDTO {
    private String title;
    private int quantity;
    private double price;
}
