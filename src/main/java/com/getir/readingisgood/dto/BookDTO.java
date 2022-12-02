package com.getir.readingisgood.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class BookDTO {
    private String title;
    private double price;
    private int stock;
}
